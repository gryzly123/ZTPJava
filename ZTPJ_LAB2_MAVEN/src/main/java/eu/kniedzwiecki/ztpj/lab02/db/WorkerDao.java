package eu.kniedzwiecki.ztpj.lab02.db;

import eu.kniedzwiecki.ztpj.lab02.entities.Director;
import eu.kniedzwiecki.ztpj.lab02.entities.EPosition;
import eu.kniedzwiecki.ztpj.lab02.entities.Salesman;
import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author knied
 */
public class WorkerDao 
{
	// ------------- REQUEST QUERIES AND PREPARED STATEMENTS ------------
	//Base Worker
	private final static String getWorker = "SELECT * FROM workers WHERE id = ?";
	private final static String getAllWorkerIds = "SELECT id FROM workers";
	private final static String insertWorker = "INSERT INTO workers(first_name, last_name, pesel,"
											 + "position_id, phone_number, service_card_number, salary) "
											 + "VALUES(?,?,?,?,?,?,?) RETURNING id";
	private final static String updateWorker = "UPDATE workers SET first_name = ?, last_name = ?, pesel = ?,"
											 + "position_id = ?, phone_number = ?, service_card_number = ?, salary = ?"
											 + "WHERE id = ?";
	private final static String deleteWorker = "DELETE FROM workers WHERE workers.id=?";
	
	private static PreparedStatement psGetWorker;
	private static PreparedStatement psGetWorkerIds;
	private static PreparedStatement psSaveWorker;
	private static PreparedStatement psUpdateWorker;
	private static PreparedStatement psDeleteWorker;
	
	//Director
	private final static String getDirector = "SELECT * FROM directors_allowances WHERE worker_id = ?";
	private final static String insertDirector = "INSERT INTO "
											+ "directors_allowances(worker_id, business_allowance, monthly_cost_limit) "
											+ "VALUES(?, ?, ?)";;
	private final static String deleteDirector = "DELETE FROM directors_allowances WHERE worker_id = ?";
	
	private static PreparedStatement psGetDirector;
	private static PreparedStatement psInsertDirector;
	private static PreparedStatement psDeleteDirector;
	
	//Salesman
	private final static String getSalesman = "SELECT * FROM tradesmans_commissions WHERE worker_id = ?";;
	private final static String insertSalesman = "INSERT INTO "
											+ "tradesmans_commissions(worker_id, commission, monthly_commission_limit) "
											+ "VALUES(?, ?, ?)";
	private final static String deleteSalesman = "DELETE FROM tradesmans_commissions WHERE worker_id = ?";
	
	private static PreparedStatement psGetSalesman;
	private static PreparedStatement psInsertSalesman;
	private static PreparedStatement psDeleteSalesman;
	
	//overridable methods for sql statements of Director and Salesman
	protected void readSubclassData() { }
	protected void updateSubclassData() { }
	protected void deleteSubclassData() { }
	protected void saveSubclassData() { }
	
	// -------------------------- DAO METHODS ----------------------------
	
	public static Optional<Worker> get(int id)
	{
		Connection c = null;
		try
		{
			c = DataSource.Get().bds.getConnection();
			Worker worker = null;
			c.setAutoCommit(false);
			if(psGetWorker == null) psGetWorker = c.prepareStatement(getWorker);
			psGetWorker.setInt(1, id);
			ResultSet result = psGetWorker.executeQuery();
			if(result.next())
			{
				//determine worker class
				int positionId = result.getInt("position_id");
				EPosition position = EPosition.getPositionFromId(positionId);
				worker = EPosition.createWorkerFromPosition(position);
				
				worker.setId(result.getInt("id"));
				worker.setFirstName(result.getString("first_name"));
				worker.setLastName(result.getString("last_name"));
				worker.setPesel(result.getString("pesel"));
				worker.setPay(Integer.parseInt(result.getString("salary")));
				worker.setPhoneNumber(result.getString("phone_number"));
				worker.setServiceCardNumber(result.getString("service_card_number"));
			}
			result.close();
			//psGetWorker.close();
			
			switch(worker.getPosition())
			{
				case Director:
					if(psGetDirector == null) psGetDirector = c.prepareStatement(getDirector);
					psGetDirector.setInt(1, id);
					ResultSet result2 = psGetDirector.executeQuery();
					if(result2.next())
					{
						Director d = (Director)worker;
						d.setBusinessAllowance(Integer.parseInt(result2.getString("business_allowance")));
						d.setMonthlyCostLimit(Integer.parseInt(result2.getString("monthly_cost_limit")));
					}
					result2.close();
					///psGetWorker.close();
					break;
					
				case Salesman:
					if(psGetSalesman == null) psGetSalesman = c.prepareStatement(getSalesman);
					psGetSalesman.setInt(1, id);
					ResultSet result3 = psGetSalesman.executeQuery();
					if(result3.next())
					{
						Salesman s = (Salesman)worker;
						s.setCommisionPercentage(Integer.parseInt(result3.getString("commission")));
						s.setCommisionLimit(Integer.parseInt(result3.getString("monthly_commission_limit")));
					}
					result3.close();
					//psGetSalesman.close();
					break;
			}
			
			c.commit();
			return Optional.of(worker);
		}
		catch(Exception e) 
		{
			System.out.println(e);
			try { c.rollback(); } catch (Exception ex) { }
			return Optional.empty();
		}
	}
	
	public static List<Worker> getAll() throws SQLException 
	{
		List<Worker> resultArray = new ArrayList<>();
		try
		{
			Connection c = DataSource.Get().getC();
			c.setAutoCommit(true);
			if(psGetWorkerIds == null) psGetWorkerIds = c.prepareStatement(getAllWorkerIds);
			ResultSet result = psGetWorkerIds.executeQuery();
			//c.commit();
			
			while(result.next())
				resultArray.add(get(result.getInt("id")).get());
			result.close();
			//psGetWorkerIds.close();
			return resultArray;
		}
		catch(Exception e) 
		{
			System.out.println(e);
			return null;
		}
	}
	
	private static int internalSave(Worker t)
	{
		Connection c = null;
		try
		{
			c = DataSource.Get().bds.getConnection();
			c.setAutoCommit(false);
			if(psSaveWorker == null) psSaveWorker = c.prepareStatement(insertWorker, PreparedStatement.RETURN_GENERATED_KEYS);
			
			psSaveWorker.setString(1, t.getFirstName());
			psSaveWorker.setString(2, t.getLastName());
			psSaveWorker.setString(3, t.getPesel());
			psSaveWorker.setInt   (4, EPosition.getIdFromPosition(t.getPosition()));
			psSaveWorker.setInt   (7, t.getPay());
			psSaveWorker.setString(5, t.getPhoneNumber());
			psSaveWorker.setString(6, t.getServiceCardNumber());
			int result = psSaveWorker.executeUpdate();
			ResultSet rs = psSaveWorker.getGeneratedKeys();
			if(!rs.next()) throw new Exception("internalSave() sql failed");
			t.setId(rs.getInt(1));
			rs.close();
			
			switch(t.getPosition())
			{
				case Director:
					if(psInsertDirector == null) psInsertDirector = c.prepareStatement(insertDirector);
					psInsertDirector.setInt(1, t.getId());
					psInsertDirector.setInt(2, ((Director)t).getBusinessAllowance());
					psInsertDirector.setInt(3, ((Director)t).getMonthlyCostLimit());
					int result2 = psInsertDirector.executeUpdate();
					if(result2 == 0) throw new Exception("insert director sql failed");
					break;
					
				case Salesman:
					if(psInsertSalesman == null) psInsertSalesman = c.prepareStatement(insertSalesman);
					psInsertSalesman.setInt(1, t.getId());
					psInsertSalesman.setInt(2, ((Salesman)t).getCommisionLimit());
					psInsertSalesman.setInt(3, ((Salesman)t).getCommisionPercentage());
					int result3 = psInsertSalesman.executeUpdate();
					if(result3 == 0) throw new Exception("insert salesman sql failed");
					break;
			}
			
			c.commit();
			return result;

		}
		catch(Exception e) 
		{
			System.out.println(e);
			try { c.rollback(); } catch (Exception ex) { }
			return -1;
		}
	}
	
	private static void internalUpdate(Worker t)
	{
		try
		{
			Connection c = DataSource.Get().bds.getConnection();
			c.setAutoCommit(false);
			if(psUpdateWorker == null) psUpdateWorker = c.prepareStatement(updateWorker);
			
			psUpdateWorker.setString(1, t.getFirstName());
			psUpdateWorker.setString(2, t.getLastName());
			psUpdateWorker.setString(3, t.getPesel());
			psUpdateWorker.setInt   (4, EPosition.getIdFromPosition(t.getPosition()));
			psUpdateWorker.setInt   (5, t.getPay());
			psUpdateWorker.setString(6, t.getPhoneNumber());
			psUpdateWorker.setString(7, t.getServiceCardNumber());
			psUpdateWorker.setInt   (8, t.getId());
			if(psUpdateWorker.executeUpdate() == 0) throw new Exception("internalUpdate() sql failed");
			c.commit();
		}
		catch(Exception e) 
		{
			System.out.println(e);
		}
	}
	
	private static void internalDelete(Worker t)
	{
		Connection c = null;
		try
		{
			c = DataSource.Get().bds.getConnection();
			c.setAutoCommit(false);
			if(psDeleteWorker == null) psDeleteWorker = c.prepareStatement(deleteWorker);
	
			psDeleteWorker.setInt(1, t.getId());
			int result = psDeleteWorker.executeUpdate();
			
			switch(t.getPosition())
			{
				case Director:
					if(psDeleteDirector == null) psDeleteDirector = c.prepareStatement(deleteDirector);
					psDeleteDirector.setInt(1, t.getId());
					int result2 = psDeleteDirector.executeUpdate();
					if(result2 == 0) throw new Exception("delete director sql failed");
					break;
					
				case Salesman:
					if(psDeleteSalesman == null) psDeleteSalesman = c.prepareStatement(deleteSalesman);
					psDeleteSalesman.setInt(1, t.getId());
					int result3 = psDeleteSalesman.executeUpdate();
					if(result3 == 0) throw new Exception("delete salesman sql failed");
					break;
			}
			
			c.commit();
			if(result == 0) throw new Exception("internalDelete() sql failed");
		}
		catch(Exception e) 
		{
			System.out.println(e);
			try { c.rollback(); } catch (Exception ex) { }
		}
	}
	
	public static void save(Worker t) throws SQLException
	{
		if(t.getId() > -1) throw new SQLException("Can't save, user already exists!");
		Connection c = DataSource.Get().bds.getConnection();
		internalSave(t);
	}
	
	public static void update(Worker t, String[] params) throws SQLException
	{
		if(t.getId() < 0) throw new SQLException("Can't update, user doesn't exist in db yet!");
		Connection c = DataSource.Get().bds.getConnection();
		internalUpdate(t);
	}
	
	public static void delete(Worker t) throws SQLException, Exception
	{
		if(t.getId() < 0) throw new SQLException("Can't delete, user doesn't exist in db yet!");
		Connection c = DataSource.Get().bds.getConnection();
		internalDelete(t);
	}
}

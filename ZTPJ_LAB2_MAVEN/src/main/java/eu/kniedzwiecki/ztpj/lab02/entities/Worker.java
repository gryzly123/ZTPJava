package eu.kniedzwiecki.ztpj.lab02.entities;

import eu.kniedzwiecki.ztpj.lab02.db.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Worker
{
	//these should be private but this is a school assignment and time is of essence
	public String pesel, firstName, lastName, phoneNumber, serviceCardNumber;
	public int id = -1, pay;
	protected final EPosition position;
	
	public String toString()
	{
		return "Id:\t\t"
				+ Integer.toString(id)
				+ "\nImie:\t\t"
				+ firstName
				+ "\nNazwisko:\t"
				+ lastName
				+ "\nTelefon:\t"
				+ phoneNumber
				+ "\nPesel:\t\t"
				+ pesel
				+ "\nNumer karty:\t"
				+ serviceCardNumber
				+ "\nPlaca:\t\t"
				+ Integer.toString(pay)
				+ "zl\nPozycja:\t"
				+ EPosition.toString(position);				
	}
	
	public void readFromStream(Scanner s)
	{
		System.out.print("\nImie:\t\t");
		firstName = s.nextLine();
		
		System.out.print("\nNazwisko:\t");
		lastName = s.nextLine();
		
		System.out.print("\nTelefon:\t");
		phoneNumber = s.nextLine();
		
		System.out.print("\nPesel:\t\t");
		pesel = s.nextLine();
		
		System.out.print("\nNumer karty:\t");
		serviceCardNumber = s.nextLine();
		
		System.out.print("\nPlaca:\t\t");
		pay = s.nextInt();
	}
	
	protected Worker(EPosition _position)
	{
		position = _position;
	}
	
	public Worker()
	{
		position = EPosition.BaseWorker;
	}

	//DAO SUPPORT
	private final static String getWorker = "SELECT * FROM workers WHERE id = ?";
	private final static String getAllWorkerIds = "SELECT id FROM workers";
	private final static String insertWorker = "INSERT INTO workers(first_name, last_name, pesel,"
											 + "position_id, phone_number, service_card_number, salary) "
											 + "VALUES(?,?,?,?,?,?,?) returning id";
	private final static String updateWorker = "UPDATE workers SET first_name = ?, last_name = ?, pesel = ?,"
											 + "position_id = ?, phone_number = ?, service_card_number = ?, salary = ?"
											 + "WHERE id = ?";
	private final static String deleteWorker = "DELETE FROM workers WHERE id = ?";

	private static PreparedStatement psGetWorker;
	private static PreparedStatement psGetWorkerIds;
	private static PreparedStatement psSaveWorker;
	private static PreparedStatement psUpdateWorker;
	private static PreparedStatement psDeleteWorker;
	
	//overridable methods for sql statements of Director and Salesman
	protected void readSubclassData() { }
	protected void updateSubclassData() { }
	protected void deleteSubclassData() { }
	protected void saveSubclassData() { }
	
	public static Optional<Worker> get(int id)
	{
		Worker worker = null;
		try
		{
			Connection c = DataSource.Get().bds.getConnection();
			c.setAutoCommit(false);
			if(psGetWorker == null) psGetWorker = c.prepareStatement(getWorker);
			psGetWorker.setInt(1, id);
			ResultSet result = psGetWorker.executeQuery();
			c.commit();
			
			if(result.next())
			{
				//determine worker class
				int positionId = result.getInt("position_id");
				EPosition position = EPosition.getPositionFromId(positionId);
				worker = EPosition.createWorkerFromPosition(position);
				
				worker.id = result.getInt("id");
				worker.firstName = result.getString("first_name");
				worker.lastName = result.getString("last_name");
				worker.pesel = result.getString("pesel");
				worker.pay = Integer.parseInt(result.getString("salary"));
				worker.phoneNumber = result.getString("phone_number");
				worker.serviceCardNumber = result.getString("service_card_number");
				
				worker.readSubclassData();
			}
			result.close();
			
			return Optional.of(worker);
		}
		catch(Exception e) 
		{
			System.out.println(e);
			return Optional.empty();
		}
	}
	
	public static List<Worker> getAll() throws SQLException 
	{
		List<Worker> resultArray = new ArrayList<>();
		try
		{
			Connection c = DataSource.Get().bds.getConnection();
			c.setAutoCommit(true);
			if(psGetWorkerIds == null) psGetWorkerIds = c.prepareStatement(getAllWorkerIds);
			ResultSet result = psGetWorkerIds.executeQuery();
			//c.commit();
			
			while(result.next())
				resultArray.add(get(result.getInt("id")).get());
			result.close();
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
		try
		{
			Connection c = DataSource.Get().bds.getConnection();
			c.setAutoCommit(false);
			if(psSaveWorker == null) psSaveWorker = c.prepareStatement(insertWorker);
			
			psSaveWorker.setString(1, t.firstName);
			psSaveWorker.setString(2, t.lastName);
			psSaveWorker.setString(3, t.pesel);
			psSaveWorker.setInt   (4, EPosition.getIdFromPosition(t.position));
			psSaveWorker.setInt   (7, t.pay);
			psSaveWorker.setString(5, t.phoneNumber);
			psSaveWorker.setString(6, t.serviceCardNumber);
			ResultSet rs = psSaveWorker.executeQuery();
			c.commit();
			
			if(!rs.next()) throw new Exception("internalSave() sql failed");
			int result = rs.getInt(1);
			rs.close();
			return result;

		}
		catch(Exception e) 
		{
			System.out.println(e);
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
			
			psUpdateWorker.setString(1, t.firstName);
			psUpdateWorker.setString(2, t.lastName);
			psUpdateWorker.setString(3, t.pesel);
			psUpdateWorker.setInt   (4, EPosition.getIdFromPosition(t.position));
			psUpdateWorker.setInt   (5, t.pay);
			psUpdateWorker.setString(6, t.phoneNumber);
			psUpdateWorker.setString(7, t.serviceCardNumber);
			psUpdateWorker.setInt   (8, t.id);
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
		try
		{
			Connection c = DataSource.Get().bds.getConnection();
			c.setAutoCommit(false);
			if(psDeleteWorker == null) psDeleteWorker = c.prepareStatement(deleteWorker);
	
			psDeleteWorker.setInt(1, t.id);
			if(!psDeleteWorker.execute()) throw new Exception("internalDelete() sql failed");
			c.commit();
		}
		catch(Exception e) 
		{
			System.out.println(e);
		}
	}
	
	public static void save(Worker t) throws SQLException
	{
		if(t.id > -1) throw new SQLException("Can't save, user already exists!");
		Connection c = DataSource.Get().bds.getConnection();
		internalSave(t);
	}
	
	public static void update(Worker t, String[] params) throws SQLException
	{
		if(t.id < 0) throw new SQLException("Can't update, user doesn't exist in db yet!");
		Connection c = DataSource.Get().bds.getConnection();
		internalUpdate(t);
	}
	
	public static void delete(Worker t) throws SQLException, Exception
	{
		if(t.id < 0) throw new SQLException("Can't delete, user doesn't exist in db yet!");
		Connection c = DataSource.Get().bds.getConnection();
		internalDelete(t);
	}
}

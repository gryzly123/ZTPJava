package eu.kniedzwiecki.ztpj.lab02.entities;
import eu.kniedzwiecki.ztpj.lab02.db.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	private final static String deleteWorker = null;

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
			PreparedStatement psGetWorker = c.prepareStatement(getWorker);
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
				worker.lastName = result.getString("last_name");
				worker.lastName = result.getString("last_name");
				
				worker.readSubclassData();
			}
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
			c.setAutoCommit(false);
			PreparedStatement psGetWorkerIds = c.prepareStatement(getAllWorkerIds);
			ResultSet result = psGetWorkerIds.executeQuery();
			c.commit();
			
			while(result.next())
				resultArray.add(get(result.getInt("id")).get());
			return resultArray;
		}
		catch(Exception e) 
		{
			System.out.println(e);
			return null;
		}
	}
	
	public static void save(Worker t) throws SQLException
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	
	public static void update(Worker t, String[] params) throws SQLException
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	
	public static void delete(Worker t) throws SQLException
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}

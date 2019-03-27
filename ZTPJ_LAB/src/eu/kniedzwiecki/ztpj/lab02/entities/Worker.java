package eu.kniedzwiecki.ztpj.lab02.entities;
import eu.kniedzwiecki.ztpj.lab02.db.Dao;
import eu.kniedzwiecki.ztpj.lab02.db.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Worker
{
	private String pesel, firstName, lastName, phoneNumber;
	private int pay;
	protected final EPosition position;
	private static WorkerDao sql;
	
	public static WorkerDao Sql()
	{
		try	{
			sql = new WorkerDao();
		} catch(SQLException e) {
			System.out.println("Warning! Could not create db statements.");
			sql = null;
		}
		return sql;
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
	static class WorkerDao implements Dao<Worker>
	{
		private PreparedStatement getWorker, getAllWorkerIds;
		private PreparedStatement insertWorker;
		private PreparedStatement deleteWorker;
		
		private Connection c;
		
		WorkerDao() throws SQLException 
		{
			c = DataSource.Get().bds.getConnection();
			c.setAutoCommit(false);
			
			getWorker       = c.prepareStatement("SELECT * FROM workers WHERE id = ?");
			getAllWorkerIds = c.prepareStatement("SELECT id FROM workers ");
			insertWorker    = c.prepareStatement(
					  "INSERT INTO workers(first_name, last_name, pesel,"
					+ "position_id, phone_number, service_card_number, salary) "
					+ "VALUES(?,?,?,?,?,?,?) returning id");
			deleteWorker    = null; //todo
			
		}
		
		@Override
		public Optional<Worker> get(long id) {
			try
			{
				getWorker.setInt(1, (int)id);
				ResultSet result = getWorker.executeQuery();
				c.commit();
				if(result.next())
				{
					int positionId = result.getInt("position_id");
					EPosition position = EPosition.getPositionFromId(positionId);
					int workerId = result.getInt("id");
					String firstName = result.getString("first_name");
					String lastName = result.getString("last_name");
					String pesel = result.getString("pesel");
					int pay = result.getInt("salary");
					String phoneNumber = result.getString("phone_number");
					String serviceCardNumber = result.getString("service_card_number");
				}
				throw new Exception("implement object creation!");
			}
			catch(Exception e) 
			{
				System.out.println(e);
				return Optional.empty();
			}
		}

		@Override
		public List<Worker> getAll() throws SQLException {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public void save(Worker t) throws SQLException {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public void update(Worker t, String[] params) throws SQLException {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}

		@Override
		public void delete(Worker t) throws SQLException {
			throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
		}
		}
}

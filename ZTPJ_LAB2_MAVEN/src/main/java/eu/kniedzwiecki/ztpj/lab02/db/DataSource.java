package eu.kniedzwiecki.ztpj.lab02.db;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {
	
	private static DataSource inst;
	private  Connection c;
	
	public static DataSource Get()
	{
		if(inst == null) inst = new DataSource();
		return inst;
	}
	
	//public Connection GetC() throws SQLException { /*c.close(); c = bds.getConnection();*/ return c; }
	
	
	public BasicDataSource bds = null;
	
	public Connection getC()
    {
		DataSource ds = DataSource.Get();
        if (c == null)
        {
            try
            {
                c = ds.bds.getConnection();
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
                return null;
            }
        }

        return c;
    }
	
	private DataSource()
	{
		try
        {
            Class.forName("org.postgresql.Driver");
			
			BasicDataSource ds = new BasicDataSource();
			ds.setUrl("jdbc:postgresql://localhost/postgres");
			ds.setUsername("postgres");
			ds.setPassword("postgres");

			ds.setMinIdle(100);
			ds.setMaxTotal(100);
			ds.setMaxIdle(100);
			ds.setMaxOpenPreparedStatements(10000);

			bds = ds;
			c = bds.getConnection();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
}

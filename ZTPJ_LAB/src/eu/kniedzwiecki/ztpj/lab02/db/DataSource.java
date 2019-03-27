package eu.kniedzwiecki.ztpj.lab02.db;
import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource {
	
	private static DataSource inst;
	
	public static DataSource Get()
	{
		if(inst == null) inst = new DataSource();
		return inst;
	}
	
	public BasicDataSource bds = null;
	
	private DataSource()
	{
		try
            {
                Class.forName("org.postgresql.Driver");
				
				BasicDataSource ds = new BasicDataSource();
				ds.setUrl("jdbc:postgresql://localhost/workers");
				ds.setUsername("postgres");
				ds.setPassword("postgres");

				ds.setMinIdle(5);
				ds.setMaxIdle(10);
				ds.setMaxOpenPreparedStatements(100);

				bds = ds;
            }
            catch (ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }

            
	}
	
}

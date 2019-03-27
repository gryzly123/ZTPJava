/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.kniedzwiecki.ztpj.lab02;

import eu.kniedzwiecki.ztpj.lab02.db.DataSource;
import eu.kniedzwiecki.ztpj.lab02.entities.EPosition;
import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author knied
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		
		System.out.println("begin");
		DataSource ds = DataSource.Get();
		
		try
		{
			List<Worker> workers = Worker.getAll();
			for(Worker w : workers)
				System.out.println((w == null) ? "internal error" : w.toString() + "\n\n");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		//try
		//{
		//	PreparedStatement ps = ds.bds.getConnection().prepareStatement("SELECT * FROM workers");
		//	ResultSet rs = ps.executeQuery();
		//	while(rs.next())
		//	{
		//		System.out.println(rs.getString("first_name"));
		//	}
		//}
		//catch(Exception e)
		//{
		//	System.out.println(e);
		//}
		
		/*
		try
		{
			EPosition p = EPosition.getPositionFromId(0);
			System.out.println(p.toString());
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
*/
	}
	
}

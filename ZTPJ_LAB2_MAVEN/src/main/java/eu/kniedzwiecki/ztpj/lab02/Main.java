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
import java.util.Scanner;

/**
 *
 * @author knied
 */
public class Main {

	static Scanner s;
	
	public static void main(String[] args)
	{
		s = new Scanner(System.in);
		int selection = 1;
		do 
		{
			System.out.println("\n\nMENU");
			System.out.println("  1: lista pracownikow");
			System.out.println("  2: dodaj pracownika");
			System.out.println("  3: usun pracownika");
			System.out.println("  4: kopia zapasowa");
			System.out.println("  5: zamknij aplikacje");
			System.out.print("Wybór > ");
//			selection = s.nextInt();
			switch(selection)
			{
				case 1:
					ListWorkers();
					break;
				case 2:
					AddWorker();
					break;
				case 3:
					RemoveWorker();
					break;
				case 4:
					Backup();
				case 5:
					return;
				default:
					System.out.println("invalid option");
			}
		}
		while(selection != 4);

		
		
		
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

	private static void ListWorkers()
	{
		DataSource ds = DataSource.Get();
		try
		{
			List<Worker> workers = Worker.getAll();
			for(Worker w : workers)
			{
				System.out.println((w == null) ? "internal error" : w.toString() + "\n\n");
				//System.in.read();
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	private static void AddWorker()
	{
		s.nextLine();
		String workerType = workerType = s.nextLine();
		try
		{
			EPosition pos = EPosition.fromString(workerType);
			Worker w = EPosition.createWorkerFromPosition(pos);
			w.readFromStream(s);
			Worker.save(w);
		}
		catch(Exception e)
		{
			System.out.println("Error adding worker: " + e.toString());
		}
	}

	private static void RemoveWorker() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private static void Backup() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}

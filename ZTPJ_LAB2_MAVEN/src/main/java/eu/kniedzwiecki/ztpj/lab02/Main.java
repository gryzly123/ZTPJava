/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.kniedzwiecki.ztpj.lab02;

import eu.kniedzwiecki.ztpj.lab02.db.DataSource;
import eu.kniedzwiecki.ztpj.lab02.db.WorkerDao;
import eu.kniedzwiecki.ztpj.lab02.entities.EPosition;
import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import java.io.IOException;
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
	static ServerDaemon sd;
	static Thread thr;
	
	public static void main(String[] args) throws IOException
	{
		s = new Scanner(System.in);
		
		System.out.println("\n\nWlaczyc serwer [1 jesli tak]? > ");
		int selection = s.nextInt();
		if(selection == 1)
		{
			sd = new ServerDaemon();
			//sd.StartDaemon();
			//sd.ListenAsync();
			thr = new Thread(sd);
			thr.start();
		}
		
		do 
		{
			System.out.println("\n\nMENU");
			System.out.println("  1: lista pracownikow");
			System.out.println("  2: dodaj pracownika");
			System.out.println("  3: usun pracownika");
			System.out.println("  4: kopia zapasowa");
			System.out.println("  5: pobierz ze zdalnego serwera");
			System.out.println("  6: zamknij aplikacje");
			System.out.print("Wybór > ");
			selection = s.nextInt();
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
					TryGetDataFromRemote();
					break;
				case 6:
					return;
				default:
					System.out.println("invalid option");
			}
		}
		while(selection != 4);
	}

	private static void PrintWorkers(List<Worker> workers) throws IOException
	{
		for(Worker w : workers)
		{
			System.out.println((w == null) ? "internal error" : w.toString() + "\n\n");
			System.in.read();
		}
	}
	
	private static void ListWorkers()
	{
		DataSource ds = DataSource.Get();
		try
		{
			List<Worker> workers = WorkerDao.getAll();
			PrintWorkers(workers);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	private static void AddWorker()
	{
		s.nextLine();
		System.out.print("Typ: [P]racownik, [D]yrektor, [H]andlowiec > ");
		String workerType = s.nextLine();
		try
		{
			EPosition pos = EPosition.BaseWorker;
			switch(workerType)
			{
				case "d": pos = EPosition.Director; break;
				case "h": pos = EPosition.Salesman; break;
			}
			Worker w = EPosition.createWorkerFromPosition(pos);
			w.readFromStream(s);
			WorkerDao.save(w);
		}
		catch(Exception e)
		{
			System.out.println("Error adding worker: " + e.toString());
		}
	}

	private static void RemoveWorker()
	{
		System.out.print("ID do usuniecia > ");
		int id = s.nextInt();
		Worker w = WorkerDao.get(id).orElse(null);
		if(w == null)
		{
			System.out.println("Nie znaleziono pracownika o takim id");
		}
		try
		{
			WorkerDao.delete(w);
			System.out.println("Usunieto");
		}
		catch(Exception e)
		{
			System.out.println("Error deleting worker:" + e.toString());
		}
	}

	private static void Backup()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private static void TryGetDataFromRemote()
	{
		String IP = "";
		while(IP.length() < 1) IP = s.nextLine();
		
		List<Worker> workers = ClientRequest.PerformTransaction(IP);
		try { PrintWorkers(workers); }
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
}

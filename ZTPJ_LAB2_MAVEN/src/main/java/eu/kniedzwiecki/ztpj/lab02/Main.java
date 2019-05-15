/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.kniedzwiecki.ztpj.lab02;

import eu.kniedzwiecki.ztpj.lab02.db.DataSource;
import eu.kniedzwiecki.ztpj.lab02.db.WorkerDao;
import eu.kniedzwiecki.ztpj.lab02.entities.*;
import eu.kniedzwiecki.ztpj.lab04.rmi.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

	static Scanner s;
	static ServerDaemon sd;
	static Thread thr;
	static RmiServer rmiServer;
	static RmiClient rmiClient;
	
	public static void main(String[] args)
	{
		s = new Scanner(System.in);
		
		System.out.println("\n\nWlaczyc serwer [1 jesli tak]? > ");
		int selection = s.nextInt();
		if(selection == 1)
		{
			//sd = new ServerDaemon();
			//thr = new Thread(sd);
			//thr.start();
			try { rmiServer = new RmiServer(); }
			catch(Exception e) { System.out.println(e.toString()); System.exit(0); }
//			try { rmiServer = new RmiServer(); }
//			catch(Exception e) { System.out.println(e.toString()); }
		}
		
		do 
		{
			System.out.println("\n\nMENU");
			System.out.println("  1: lista pracownikow");
			System.out.println("  2: dodaj pracownika");
			System.out.println("  3: usun pracownika");
			System.out.println("  4: kopia zapasowa");
			System.out.println("  5: pobierz ze zdalnego serwera (TCP)");
			System.out.println("  6: pobierz ze zdalnego serwera (RMI)");
			System.out.println("  7: zamknij aplikacje");
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
					TryGetDataFromRemoteTcp();
					break;
				case 6:
					TryGetDataFromRemoteRmi();
					break;
				case 7:
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

	private static void TryGetDataFromRemoteTcp()
	{
		String IP = "";
		while(IP.length() < 1) IP = s.nextLine();
		
		List<Worker> workers = ClientRequest.PerformTransaction(IP);
		try { PrintWorkers(workers); }
		catch (Exception e)
		{
			System.out.println(e.toString());
			System.exit(0);
		}
	}

	private static void TryGetDataFromRemoteRmi()
	{
		try 
		{
			if(rmiClient == null) rmiClient = new RmiClient();
			
			s.nextLine();
			
			System.out.print("Uzytkownik: ");
			String username = s.nextLine();
			String password;

			System.out.print("Haslo: ");
			if(System.console() != null)
				password = new String(System.console().readPassword());
			else
				password = s.nextLine();
			
			if(!rmiClient.Login(username, password))
			{
				System.out.println("Login error: user doesn't exist");
				return;
			}
			
			List<Worker> workers = rmiClient.FetchWorkers();
			if(workers != null)
				PrintWorkers(workers);
			else
				System.out.println("Fetch failed, null received from server.");
			
		} catch (RemoteException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NotBoundException | IOException e) {
			System.out.println(e.toString());
			System.exit(0);
		}
	}
	
}

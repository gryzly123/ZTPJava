/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.kniedzwiecki.ztpj.lab02;

import eu.kniedzwiecki.ztpj.lab02.db.WorkerDao;
import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import java.io.*;
import java.net.*;
import java.util.List;
import java.beans.XMLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerDaemon implements Runnable
{
	ServerSocket as;
	boolean initialized = false;
	
	public void StartDaemon()
	{
		try
		{
			as = new ServerSocket(1112);
		
			System.out.println("Server listening at "
				+ InetAddress.getLocalHost() 
				+ ":"
				+ as.getLocalPort());
			
			initialized = true;
		}
		catch (Exception ex)
		{
			Logger.getLogger(ServerDaemon.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void ListenAsync()
	{
		while(initialized)
		{
			try
			{
				Socket s = as.accept();
				System.out.println("<connection established>");
				DataInputStream dis = new DataInputStream(s.getInputStream());
				String msg = dis.readUTF();
				
				if(msg.contains("RECEIVE_DB"))
				{
					DataOutputStream dos = new DataOutputStream(s.getOutputStream());
					dos.writeUTF(ServeXmlDb());
					System.out.println("<request received, sending data>");
					dos.close();
				}
				
				dis.close();
				s.close();
				System.out.println("<connection ended>");
			}
			catch(Exception e)
			{
				System.out.println(e.toString());
			}
		}
	}
	
	public String ServeXmlDb()
	{
		List<Worker> Workers = null;
		
		try
		{
			Workers = WorkerDao.getAll();
			
			XMLEncoder encoder = null;
			ByteArrayOutputStream BOS = new ByteArrayOutputStream();
			encoder = new XMLEncoder(new BufferedOutputStream(BOS));
			encoder.writeObject(Workers);
			encoder.close();
			return BOS.toString("UTF-8");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
	}

	@Override
	public void run()
	{
		StartDaemon();
		ListenAsync();
	}
}

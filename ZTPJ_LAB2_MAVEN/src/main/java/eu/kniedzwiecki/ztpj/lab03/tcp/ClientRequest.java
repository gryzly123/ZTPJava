package eu.kniedzwiecki.ztpj.lab03.tcp;

import eu.kniedzwiecki.ztpj.lab02.db.WorkerDao;
import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.*;


public class ClientRequest
{
	public static List<Worker> PerformTransaction(String IP)
	{
		List<Worker> Result = null;
		
		try
		{
			Socket s = new Socket(IP, 1112);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF("RECEIVE_DB");
			
			DataInputStream dis = new DataInputStream(s.getInputStream());
			String response = dis.readUTF();
			
			InputStream stream = new ByteArrayInputStream(response.getBytes(StandardCharsets.UTF_8));
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(stream));
			Result = (List<Worker>)decoder.readObject();
			
			dis.close();
			dos.close();
			s.close();
		}
		catch (Exception ex)
		{
			Logger.getLogger(ServerDaemon.class.getName()).log(Level.SEVERE, null, ex);
		}
		return Result;
	}
}

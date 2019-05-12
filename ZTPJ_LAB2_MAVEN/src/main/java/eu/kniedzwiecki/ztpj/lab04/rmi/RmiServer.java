package eu.kniedzwiecki.ztpj.lab04.rmi;

import eu.kniedzwiecki.ztpj.lab04.auth.*;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer 
{
	final Authenticator a;
	final WorkerFetch wf;
	final Registry r;
	
	public RmiServer() throws RemoteException, MalformedURLException, AlreadyBoundException
	{
		a  = new Authenticator();
		wf = new WorkerFetch(a);
		
		System.setProperty("java.rmi.server.hostname", "127.0.0.1");
		r = LocateRegistry.createRegistry(1112);
		
		//UnicastRemoteObject.exportObject(a, 0);
		//UnicastRemoteObject.exportObject(wf, 0);
		
		Naming.bind("rmi://127.0.0.1:1112/auth", a);
		Naming.bind("rmi://127.0.0.1:1112/wf", wf);	
	}	
}

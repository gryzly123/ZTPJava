package eu.kniedzwiecki.ztpj.lab04.rmi;

import eu.kniedzwiecki.ztpj.lab04.auth.*;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;

public class RmiServer 
{
	final Authenticator a;
	final WorkerFetch wf;
	final Registry r;
	
	public static final int rmiPort = 1099;
	public static final String rmiHost = "localhost";
	
	public RmiServer() throws RemoteException, MalformedURLException, AlreadyBoundException, InterruptedException
	{
		a  = new Authenticator();
		wf = new WorkerFetch(a);
		
		System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
		
		r = LocateRegistry.createRegistry(rmiPort);
		Naming.rebind("//localhost/auth", a );
		Naming.rebind("//localhost/wf",   wf);
	}
}

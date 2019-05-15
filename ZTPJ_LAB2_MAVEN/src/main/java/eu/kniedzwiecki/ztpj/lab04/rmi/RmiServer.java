package eu.kniedzwiecki.ztpj.lab04.rmi;

import eu.kniedzwiecki.ztpj.lab04.auth.*;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;

public class RmiServer 
{
	final Authenticator a;
	final WorkerFetch wf;
	final Registry r;
	
	public static final int rmiPort = 1099;
	//public static final String rmiHost = "192.168.0.27";
	public static final String rmiHost = "localhost";
	
	public RmiServer() throws RemoteException, MalformedURLException, AlreadyBoundException, InterruptedException
	{
		a  = new Authenticator();
		wf = new WorkerFetch(a);
		
		System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
		
		//System.setProperty("java.rmi.server.hostname", rmiHost);
		//System.setProperty(" java.rmi.server.ignoreStubClasses ", "true");
		//System.setProperty("java.security.policy","file:/rmi.policy");
		
		r = LocateRegistry.createRegistry(rmiPort);
		
		//r = LocateRegistry.getRegistry(rmiPort);//use any no. less than 55000
		r.list();
		
		Naming.rebind("//localhost/auth", a );
		Naming.rebind("//localhost/wf",   wf);
	}
}

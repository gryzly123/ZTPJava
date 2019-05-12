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
	
	public RmiServer() throws RemoteException, MalformedURLException, AlreadyBoundException, InterruptedException
	{
		a  = new Authenticator();
		wf = new WorkerFetch(a);
		
		System.setProperty("java.rmi.server.hostname", "127.0.0.1");
		System.setProperty(" java.rmi.server.ignoreStubClasses ", "true");
		System.setProperty("java.security.policy","file:/rmi.policy");
		r = LocateRegistry.createRegistry(1112);
		
		Naming.rebind("auth", a );
		Naming.rebind("wf",   wf);
	}	
}

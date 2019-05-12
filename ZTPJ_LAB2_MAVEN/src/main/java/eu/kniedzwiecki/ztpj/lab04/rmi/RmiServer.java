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
		TimeUnit.SECONDS.sleep(1);
		r = LocateRegistry.createRegistry(1112);
		TimeUnit.SECONDS.sleep(1);
		
		//Nie crashuje, ale klient nie znajduje tego lookupem
		
		Naming.rebind("rmi://127.0.0.1:1112/auth", a);
		Naming.rebind("rmi://127.0.0.1:1112/wf", wf);	
		
		//Crashuje (oba warianty):
		
		//java.rmi.ConnectException: Connection refused to host: 192.168.56.1; nested exception is: 
		//		java.net.ConnectException: Connection refused: connect
		
		//Naming.rebind("//127.0.0.1/auth", a );
		//Naming.rebind("//127.0.0.1/wf",   wf);
		
		//Naming.rebind("auth", a );
		//Naming.rebind("wf",   wf);
	}	
}

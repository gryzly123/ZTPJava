package eu.kniedzwiecki.ztpj.lab04.rmi;

import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import eu.kniedzwiecki.ztpj.lab04.auth.Authenticator;
import eu.kniedzwiecki.ztpj.lab04.auth.WorkerFetch;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class RmiClient 
{
	Authenticator remoteAuth;
	WorkerFetch remoteWf;
	String sessionToken;
	
	public RmiClient() throws RemoteException, NotBoundException
	{
		Registry r = LocateRegistry.getRegistry("127.0.0.1", 1112);
		
		remoteAuth = (Authenticator) r.lookup("rmi://127.0.0.1:1112/auth");
		remoteWf   = (WorkerFetch)   r.lookup("rmi://127.0.0.1:1112/wf");
	}
	
	public boolean Login(String username, String password) throws RemoteException
	{
		if(remoteAuth == null) return false;
		sessionToken = remoteAuth.authenticate(username, password);
		return sessionToken != null;
	}
	
	public List<Worker> FetchWorkers() throws RemoteException
	{
		if(sessionToken == null) return null;
		if(remoteWf == null) return null;
		return remoteWf.getAllWorkers(sessionToken);
	}
	
}

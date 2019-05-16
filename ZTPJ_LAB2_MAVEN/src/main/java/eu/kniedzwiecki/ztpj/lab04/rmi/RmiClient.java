package eu.kniedzwiecki.ztpj.lab04.rmi;

import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import eu.kniedzwiecki.ztpj.lab04.auth.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.List;

public class RmiClient 
{
	IAuthenticator remoteAuth;
	IWorkerFetch remoteWf;
	String sessionToken;
	
	public RmiClient() throws RemoteException, NotBoundException
	{
		Registry r = LocateRegistry.getRegistry(RmiServer.rmiHost, RmiServer.rmiPort);
		remoteAuth = (IAuthenticator) r.lookup("auth");
		remoteWf   = (IWorkerFetch)   r.lookup("wf");
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

package eu.kniedzwiecki.ztpj.lab04.auth;

import eu.kniedzwiecki.ztpj.lab02.db.WorkerDao;
import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import eu.kniedzwiecki.ztpj.lab04.auth.Authenticator.Token;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author knied
 */
public class WorkerFetch extends UnicastRemoteObject implements IWorkerFetch
{
	private final Authenticator a;
	
	public WorkerFetch(Authenticator _a) throws RemoteException
	{
		a = _a;
	}

	//@Override
	public List<Worker> getAllWorkers(String token) throws RemoteException
	{
		Token t = a.getTokenData(token);
		if(t == null) return null;
		if(!t.IsValid()) return null;
		t.Use();
		try
		{
			System.out.println("RMI getAllWorkers() executed."); 
			return WorkerDao.getAll();
		}
		catch(Exception e)
		{
			System.out.println("RMI getAllWorkers failed due to SQL error: " 
					+ e.toString());
			return null;
		}
	}
}

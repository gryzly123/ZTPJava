package eu.kniedzwiecki.ztpj.lab04.auth;

import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IWorkerFetch extends Remote
{
	List<Worker> getAllWorkers(String token) throws RemoteException;
}

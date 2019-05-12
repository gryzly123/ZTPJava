/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.kniedzwiecki.ztpj.lab04.auth;

import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IWorkerFetch extends Remote
{
	List<Worker> getAllWorkers(String token) throws RemoteException;
}

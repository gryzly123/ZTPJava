package eu.kniedzwiecki.ztpj.lab04.auth;
import java.rmi.*;

public interface IAuthenticator extends Remote
{
	//returns auth token if authentication successful; null otherwise
	String authenticate(String username, String password) throws RemoteException;
}

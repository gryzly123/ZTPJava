package eu.kniedzwiecki.ztpj.lab07.jaxws;

import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import eu.kniedzwiecki.ztpj.lab04.auth.Authenticator;
import eu.kniedzwiecki.ztpj.lab04.auth.WorkerFetch;
import eu.kniedzwiecki.ztpj.lab06.jaxb.Marshal;
import eu.kniedzwiecki.ztpj.lab06.jaxb.WorkerDb;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService(
		serviceName = "workers",
		portName = "1114",
		endpointInterface = "eu.kniedzwiecki.ztpj.lab07.jaxws.IWorkersWebService",
		targetNamespace="http://localhost/")
public class WorkersWebServiceImpl implements IWorkersWebService
{
	final Authenticator auth;
	final WorkerFetch wf;
	
	//wymagany konstruktor bez argumentów
	public WorkersWebServiceImpl() throws Exception
	{
		try
		{
			auth = new Authenticator();
			wf = new WorkerFetch(auth);
		}
		catch(Exception e)
		{
			throw new Exception("authenticator_init_failed");
		}
	}

	@WebMethod
	public String AuthUser(String login, String password)
	{
		try {
			return auth.authenticate(login, password);
		} catch (RemoteException ex) {
			return null;
		}
	}
	
	@WebMethod
	public String GetWorkersXml(String authToken)
	{
		try {
			List<Worker> workers = wf.getAllWorkers(authToken);
			StringWriter writer = Marshal.CreateWriterStr();
			WorkerDb t = new WorkerDb();
			t.setWorkers(workers);
			Marshal.MarshallWorkers(t, writer);
			return writer.toString();
		
		} catch (Exception e) {
			return null;
		}
		
	}
}


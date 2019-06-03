package eu.kniedzwiecki.ztpj.lab07.jaxws;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService(serviceName = "workers", portName = "1114", targetNamespace="http://localhost/")
public class WorkersWebService
{
	//wymagany konstruktor bez argumentów
	public WorkersWebService()
	{
	}

	@WebMethod
	public String AuthUser(String login, String password)
	{
		return "hello";
	}
	
	@WebMethod
	public String GetWorkersXml(String authToken)
	{
		return "world";
	}
}


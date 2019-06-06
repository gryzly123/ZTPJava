package eu.kniedzwiecki.ztpj.lab07.jaxws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IWorkersWebService
{
	@WebMethod
	public String AuthUser(String login, String password);
	
	@WebMethod
	public String GetWorkersXml(String authToken);
}


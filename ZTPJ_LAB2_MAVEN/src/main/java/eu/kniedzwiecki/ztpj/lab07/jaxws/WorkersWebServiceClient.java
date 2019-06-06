package eu.kniedzwiecki.ztpj.lab07.jaxws;

import eu.kniedzwiecki.ztpj.lab06.jaxb.Marshal;
import eu.kniedzwiecki.ztpj.lab06.jaxb.WorkerDb;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class WorkersWebServiceClient implements IWorkersWebService
{
	final URL wsdlUrl;
	final IWorkersWebService workersJaxws;
	
	public WorkersWebServiceClient() throws MalformedURLException
	{
		wsdlUrl = new URL("http://localhost:1114/ztpj_lab2_maven/WorkersService?wsdl");
		QName qName = new QName("http://localhost/", "workers"); 
		Service service = Service.create(wsdlUrl, qName);
		workersJaxws = service.getPort(IWorkersWebService.class);
	}

	@Override
	public String AuthUser(String login, String password)
	{
		return workersJaxws.AuthUser(login, password);
	}

	@Override
	public String GetWorkersXml(String authToken)
	{
		return workersJaxws.GetWorkersXml(authToken);
	}		
}

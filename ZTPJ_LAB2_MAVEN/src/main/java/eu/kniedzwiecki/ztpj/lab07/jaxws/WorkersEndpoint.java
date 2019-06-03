package eu.kniedzwiecki.ztpj.lab07.jaxws;

import javax.xml.ws.Endpoint;


public class WorkersEndpoint
{
	final String serviceUrl = "http://localhost:1114/ztpj_lab2_maven/WorkersService";
	/*final*/ Endpoint endpoint;
	
	public WorkersEndpoint()
	{
		try
		{
			endpoint = Endpoint.publish(serviceUrl, new WorkersWebServiceImpl());
			System.out.println("JAX-WebService started: " + serviceUrl);
		}
		catch(Exception e)
		{
			endpoint = null;
			System.out.println("JAX-WebService failed to start: " + e.toString());
		}
	}
}

package eu.kniedzwiecki.ztpj.lab06.jaxb;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.*;

public class Marshal {
	
	static JAXBContext jaxbContext;
	static Marshaller marshaller;
	static Unmarshaller unmarshaller;
	
	public static void init() throws PropertyException, JAXBException
	{
		if(jaxbContext == null)
		{
			jaxbContext = JAXBContext.newInstance(WorkerDb.class);
			marshaller = jaxbContext.createMarshaller();
			unmarshaller = jaxbContext.createUnmarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		}
	}
	
	public static void MarshallWorkers(WorkerDb t, Writer xmlWriter) throws PropertyException, JAXBException
	{
		init();
		marshaller.marshal(t, xmlWriter);
	}
	
	public static WorkerDb UnmarshallWorkers(Reader xmlReader) throws PropertyException, JAXBException
	{
		init();
		return (WorkerDb)unmarshaller.unmarshal(xmlReader);
	}
	
	public static FileWriter CreateWriter(String filename) throws IOException
	{
		return new FileWriter("./" + filename);
	}
	
	public static FileReader CreateReader(String filename) throws FileNotFoundException
	{
		return new FileReader("./" + filename);
	}
	
	public static StringWriter CreateWriterStr()
	{
		return new StringWriter();
	}
	
	public static StringReader CreateReaderStr(String fromString)
	{
		return new StringReader(fromString);
	}
}

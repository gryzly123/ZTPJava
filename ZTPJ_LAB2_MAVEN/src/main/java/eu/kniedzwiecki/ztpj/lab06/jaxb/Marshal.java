/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.kniedzwiecki.ztpj.lab06.jaxb;

import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	
	public static void MarshallWorkers(WorkerDb t, FileWriter xmlWriter) throws PropertyException, JAXBException
	{
		init();
		marshaller.marshal(t, xmlWriter);
	}
	
	public static WorkerDb UnmarshallWorkers(FileReader xmlReader) throws PropertyException, JAXBException
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
}

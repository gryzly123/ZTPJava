/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.kniedzwiecki.ztpj.lab06.jaxb;

import eu.kniedzwiecki.ztpj.lab02.entities.Worker;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "workers")
public class WorkerDb {
	
	private List<Worker> workers = new ArrayList<Worker>();

	public List<Worker> getWorkers() {
		return workers;
	}

	@XmlElement(name="data")
	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}
}

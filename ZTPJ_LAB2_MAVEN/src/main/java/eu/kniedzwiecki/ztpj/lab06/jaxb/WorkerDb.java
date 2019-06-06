package eu.kniedzwiecki.ztpj.lab06.jaxb;

import eu.kniedzwiecki.ztpj.lab02.entities.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "workers")
public class WorkerDb {
	
	private List<Worker> workers = new ArrayList<>();

	public List<Worker> getWorkers() {
		return workers;
	}

	@XmlElements({
            @XmlElement(name = "worker", type = Worker.class),
            @XmlElement(name = "director", type = Director.class),
            @XmlElement(name = "salesman", type = Salesman.class)
    })
	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}
}

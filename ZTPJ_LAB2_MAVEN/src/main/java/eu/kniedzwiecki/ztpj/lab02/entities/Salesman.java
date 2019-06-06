package eu.kniedzwiecki.ztpj.lab02.entities;

import java.util.Scanner;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "director")
@XmlType(propOrder = { "commisionPercentage", "commisionLimit"})
public class Salesman extends Worker
{

	public int getCommisionPercentage() {
		return commisionPercentage;
	}

	public int getCommisionLimit() {
		return commisionLimit;
	}

	@XmlAttribute
	public void setCommisionPercentage(int commisionPercentage) {
		this.commisionPercentage = commisionPercentage;
	}

	@XmlAttribute
	public void setCommisionLimit(int commisionLimit) {
		this.commisionLimit = commisionLimit;
	}
	private int commisionPercentage;
	private int commisionLimit;
	
	public Salesman()
	{
		super(EPosition.Salesman);
	}
	
	public void readFromStream(Scanner s)
	{
		super.readFromStream(s);

		System.out.print("Prowizja %:\t\t");
		setCommisionPercentage(s.nextInt());
		
		System.out.print("Miesieczy limit prowizji (zl):\t\t");
		setCommisionLimit(s.nextInt());
	}
		
	public String toString()
	{
		return super.toString()
				+ "\nProwizja %:\t\t"
				+ Integer.toString(getCommisionPercentage())
				+ "\nMiesieczy limit prowizji (zl):\t\t"
				+ Integer.toString(getCommisionLimit());
	}
}

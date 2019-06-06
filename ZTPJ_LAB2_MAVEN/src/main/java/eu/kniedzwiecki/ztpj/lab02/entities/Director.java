package eu.kniedzwiecki.ztpj.lab02.entities;

import java.util.Scanner;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "director")
@XmlType(propOrder = { "businessAllowance", "monthlyCostLimit"})
public class Director extends Worker
{

	public int getBusinessAllowance() {
		return businessAllowance;
	}

	public int getMonthlyCostLimit() {
		return monthlyCostLimit;
	}

	@XmlAttribute
	public void setBusinessAllowance(int businessAllowance) {
		this.businessAllowance = businessAllowance;
	}

	@XmlAttribute
	public void setMonthlyCostLimit(int monthlyCostLimit) {
		this.monthlyCostLimit = monthlyCostLimit;
	}
	private int businessAllowance;
	private int monthlyCostLimit;
	
	public Director()
	{
		super(EPosition.Director);
	}
	
	public void readFromStream(Scanner s)
	{
		super.readFromStream(s);

		System.out.print("Dodatek sluzbowy:\t\t");
		setBusinessAllowance(s.nextInt());
		
		System.out.print("Miesieczny limit kosztow:\t\t");
		setMonthlyCostLimit(s.nextInt());
	}
	
	public String toString()
	{
		return super.toString()
				+ "\nDodatek sluzbowy:\t\t"
				+ Integer.toString(getBusinessAllowance())
				+ "\nMiesieczny limit kosztow:\t\t"
				+ Integer.toString(getMonthlyCostLimit());
	}
}

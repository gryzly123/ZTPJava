package eu.kniedzwiecki.ztpj.lab02.entities;

import java.util.Scanner;

public class Director extends Worker
{
	private int businessAllowance, monthlyCostLimit;
	
	public Director()
	{
		super(EPosition.Director);
	}
	
	public void readFromStream(Scanner s)
	{
		super.readFromStream(s);

		System.out.print("Dodatek sluzbowy:\t\t");
		businessAllowance = s.nextInt();
		
		System.out.print("Miesieczny limit kosztow:\t\t");
		monthlyCostLimit = s.nextInt();
	}
	
	public String toString()
	{
		return super.toString()
				+ "\nDodatek sluzbowy:\t\t"
				+ Integer.toString(businessAllowance)
				+ "\nMiesieczny limit kosztow:\t\t"
				+ Integer.toString(monthlyCostLimit);
	}
}

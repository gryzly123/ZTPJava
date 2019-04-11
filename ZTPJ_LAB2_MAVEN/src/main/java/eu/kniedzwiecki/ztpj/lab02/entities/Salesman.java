package eu.kniedzwiecki.ztpj.lab02.entities;

import java.util.Scanner;

public class Salesman extends Worker
{
	public int commisionPercentage, commisionLimit;
	
	public Salesman()
	{
		super(EPosition.Salesman);
	}
	
	public void readFromStream(Scanner s)
	{
		super.readFromStream(s);

		System.out.print("Prowizja %:\t\t");
		commisionPercentage = s.nextInt();
		
		System.out.print("Miesieczy limit prowizji (zl):\t\t");
		commisionLimit = s.nextInt();
	}
		
	public String toString()
	{
		return super.toString()
				+ "\nProwizja %:\t\t"
				+ Integer.toString(commisionPercentage)
				+ "\nMiesieczy limit prowizji (zl):\t\t"
				+ Integer.toString(commisionLimit);
	}
}

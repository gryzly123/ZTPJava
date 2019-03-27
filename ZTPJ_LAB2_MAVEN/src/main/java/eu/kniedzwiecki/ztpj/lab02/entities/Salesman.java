package eu.kniedzwiecki.ztpj.lab02.entities;

public class Salesman extends Worker
{
	private int commisionPercentage, commisionLimit;
	
	public Salesman()
	{
		super(EPosition.Salesman);
	}
}

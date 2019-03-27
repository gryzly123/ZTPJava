package eu.kniedzwiecki.ztpj.lab02.entities;

public class Director extends Worker
{
	private int businessAllowance, monthlyCostLimit;
	private String cardNumber;
	
	public Director()
	{
		super(EPosition.Director);
	}
}

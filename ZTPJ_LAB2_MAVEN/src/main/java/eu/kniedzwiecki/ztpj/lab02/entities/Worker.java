package eu.kniedzwiecki.ztpj.lab02.entities;
import java.io.Serializable;
import java.util.Scanner;

public class Worker implements Serializable
{
	//these should be private but this is a school assignment and time is of essence
	public String pesel, firstName, lastName, phoneNumber, serviceCardNumber;
	public int id = -1, pay;
	public final EPosition position;
	
	public String toString()
	{
		return "Id:\t\t"
				+ Integer.toString(id)
				+ "\nImie:\t\t"
				+ firstName
				+ "\nNazwisko:\t"
				+ lastName
				+ "\nTelefon:\t"
				+ phoneNumber
				+ "\nPesel:\t\t"
				+ pesel
				+ "\nNumer karty:\t"
				+ serviceCardNumber
				+ "\nPlaca:\t\t"
				+ Integer.toString(pay)
				+ "zl\nPozycja:\t"
				+ EPosition.toString(position);				
	}
	
	public void readFromStream(Scanner s)
	{
		System.out.print("Imie:\t\t");
		firstName = s.nextLine();
		
		System.out.print("Nazwisko:\t");
		lastName = s.nextLine();
		
		System.out.print("Telefon:\t");
		phoneNumber = s.nextLine();
		
		System.out.print("Pesel:\t\t");
		pesel = s.nextLine();
		
		System.out.print("Numer karty:\t");
		serviceCardNumber = s.nextLine();
		
		System.out.print("Placa:\t\t");
		pay = s.nextInt();
	}
	
	protected Worker(EPosition _position)
	{
		position = _position;
	}
	
	public Worker()
	{
		position = EPosition.BaseWorker;
	}

	
}

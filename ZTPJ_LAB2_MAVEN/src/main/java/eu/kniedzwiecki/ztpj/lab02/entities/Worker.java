package eu.kniedzwiecki.ztpj.lab02.entities;

import eu.kniedzwiecki.ztpj.lab02.db.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Worker
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
		System.out.print("\nImie:\t\t");
		firstName = s.nextLine();
		
		System.out.print("\nNazwisko:\t");
		lastName = s.nextLine();
		
		System.out.print("\nTelefon:\t");
		phoneNumber = s.nextLine();
		
		System.out.print("\nPesel:\t\t");
		pesel = s.nextLine();
		
		System.out.print("\nNumer karty:\t");
		serviceCardNumber = s.nextLine();
		
		System.out.print("\nPlaca:\t\t");
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

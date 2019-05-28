package eu.kniedzwiecki.ztpj.lab02.entities;
import java.io.Serializable;
import java.util.Scanner;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "worker")
@XmlType(propOrder = { "id", "firstName", "lastName", "phoneNumber", "serviceCardNumber", "pay"})
public class Worker implements Serializable
{
	//these should be private but this is a school assignment and time is of essence
	private   String pesel, firstName, lastName, phoneNumber, serviceCardNumber;
	private   int id = -1, pay;
	private final EPosition position;
	
	public String toString()
	{
		return "Id:\t\t"
				+ Integer.toString(getId())
				+ "\nImie:\t\t"
				+ getFirstName()
				+ "\nNazwisko:\t"
				+ getLastName()
				+ "\nTelefon:\t"
				+ getPhoneNumber()
				+ "\nPesel:\t\t"
				+ getPesel()
				+ "\nNumer karty:\t"
				+ getServiceCardNumber()
				+ "\nPlaca:\t\t"
				+ Integer.toString(getPay())
				+ "zl\nPozycja:\t"
				+ EPosition.toString(getPosition());				
	}
	
	public void readFromStream(Scanner s)
	{
		System.out.print("Imie:\t\t");
		setFirstName(s.nextLine());
		
		System.out.print("Nazwisko:\t");
		setLastName(s.nextLine());
		
		System.out.print("Telefon:\t");
		setPhoneNumber(s.nextLine());
		
		System.out.print("Pesel:\t\t");
		setPesel(s.nextLine());
		
		System.out.print("Numer karty:\t");
		setServiceCardNumber(s.nextLine());
		
		System.out.print("Placa:\t\t");
		setPay(s.nextInt());
	}
	
	protected Worker(EPosition _position)
	{
		position = _position;
	}
	
	public Worker()
	{
		position = EPosition.BaseWorker;
	}

	public String getPesel() {
		return pesel;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getServiceCardNumber() {
		return serviceCardNumber;
	}

	public int getId() {
		return id;
	}

	public int getPay() {
		return pay;
	}

	public EPosition getPosition() {
		return position;
	}

	@XmlAttribute
	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	@XmlAttribute
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlAttribute
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlAttribute
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@XmlAttribute
	public void setServiceCardNumber(String serviceCardNumber) {
		this.serviceCardNumber = serviceCardNumber;
	}

	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute
	public void setPay(int pay) {
		this.pay = pay;
	}
}

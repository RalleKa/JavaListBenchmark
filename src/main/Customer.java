package main;

public final class Customer {
	private int personalNumber;
	
	public Customer(int personalNumber) {
		setPersonalNumber(personalNumber);
	}
	
	public void setPersonalNumber(int personalNumber) {
		this.personalNumber = personalNumber;
	}
	
	public int getPersonalNumber() {
		return this.personalNumber;
	}
}

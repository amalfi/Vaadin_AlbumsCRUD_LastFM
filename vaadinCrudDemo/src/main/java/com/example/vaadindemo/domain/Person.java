package com.example.vaadindemo.domain;

public class Person {
	
	private int id;
	
	private String firstName;
	
	private String yob;
	
	private String lastName;
	
	public Person(int id, String firstName, String yob, String lastName) {
		super();
		this.id=id;
		this.firstName = firstName;
		this.yob = yob;
		this.lastName = lastName;
	}
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Person() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getYob() {
		return yob;
	}

	public void setYob(String yob) {
		this.yob = yob;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person [id="+id+", firstName=" + firstName + ", yob=" + yob
				+ ", lastName=" + lastName + "]";
	}

}

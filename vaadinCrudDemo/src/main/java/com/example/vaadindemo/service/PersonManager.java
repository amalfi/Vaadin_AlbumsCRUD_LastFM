package com.example.vaadindemo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.vaadindemo.domain.Person;

public class PersonManager {
	
	private List<Person> db = new ArrayList<Person>();
	
	private int getCurrentId(List db)
	{
		int currentId;
		currentId = db.size();
		return currentId;
	}
	
	public void addPerson(Person person)
	{
		Person p = new Person(getCurrentId(db), person.getFirstName(), person.getYob(), person.getLastName());
		db.add(p);
	}
	
	public List<Person> saveChanges(Person person)
	{
		for(Person p : db)
		{	
			if(p.getId()==person.getId())
			{				
				int index = db.indexOf(p);
				db.set(index, person);
			}
		}
		return db;
	}
	
	public List<Person> deletePerson(Person person){
 		Person toRemove = null;
		for(Person p : db)
 		{
 			if (person.getFirstName().equals(p.getFirstName()) && person.getLastName().equals(p.getLastName()))
 			{
 				toRemove = p;
 			}
 		}
		db.remove(toRemove);
 		return db;
	}
	
	public List<Person> findAll(){
		return db;
	}

}

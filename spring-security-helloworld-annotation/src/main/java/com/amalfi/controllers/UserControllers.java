package com.amalfi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.amalfi.hibernate.HibernateUtil;
import com.amalfi.model.User;


public class UserControllers 
{
	
	public static boolean checkIfUserAlreadyExistInDB(String username)
	{
		boolean bUserExist=false;
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		 
		Query query = session.createQuery("from User where login = :login ");
		query.setParameter("login", username );
		 //jezeli lista jest pusta nie ma Usera o takim loginie w bazie i mozna rejestrowac
		List list = query.list();
		if(list.size()!=0)
		{
			for(int i=0; i<list.size(); i++)
			{
				String CucrrentElement = String.valueOf(list.get(i));
			}
		}

		session.disconnect();
		return bUserExist;
	}

	public static List<User> getUserFromDB()
	{
		List<User> users = new ArrayList<User>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		User user=null;
		Query query = session.createQuery("from User where");
		 //jezeli lista jest pusta nie ma Usera o takim loginie w bazie i mozna rejestrowac
		List list = query.list();
	
		if(list.size()!=0)
		{
			for(int i=0; i<list.size(); i++)
			{
				User currentUser = (User) list.get(i);
				users.add(currentUser);
				System.out.println("test");
			}
		}

		session.disconnect();
		
		return users;
	}
}

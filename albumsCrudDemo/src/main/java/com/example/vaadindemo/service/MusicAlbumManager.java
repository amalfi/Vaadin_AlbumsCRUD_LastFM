package com.example.vaadindemo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.vaadindemo.domain.MusicAlbum;
import com.example.vaadindemo.parser.XMLDOMParser;
import com.example.vaadindemo.request.RequestService;

public class MusicAlbumManager {
	
	private List<MusicAlbum> db = new ArrayList<MusicAlbum>();
	
	private int getCurrentId(List db)
	{
		int currentId;
		currentId = db.size();
		return currentId;
	}
	
	public void addMusicAlbum(MusicAlbum musicAlbum)
	{
		//MusicAlbum ma = new MusicAlbum(getCurrentId(db), musicAlbum.getAlbumName(), musicAlbum.getArtistName(), musicAlbum.getImage());
		db.add(musicAlbum);
	}
	
	public List<MusicAlbum> saveChanges(MusicAlbum musicAlbum)
	{
		if(db.isEmpty())
		{
			db.add(musicAlbum);
		}
		for(MusicAlbum p : db)
		{	
			if(p.getId()==musicAlbum.getId())
			{				
				int index = db.indexOf(p);
				db.set(index, musicAlbum);
			}
		}
		
		return db;
	}
	
	public List<MusicAlbum> deletePerson(MusicAlbum musicAlbum){
 		MusicAlbum toRemove = null;
		for(MusicAlbum ma : db)
 		{
			if (ma.getId()==musicAlbum.getId())
 			{
 				toRemove = ma;
 			}
 		}
		db.remove(toRemove);
 		return db;
	}
	
	public List<MusicAlbum> findAll(){
		return db;
	}
	
	public ArrayList<MusicAlbum> returnAllAlbums(String sArtistName)
	{
		
		XMLDOMParser xdp = new XMLDOMParser();
		RequestService rs = new RequestService();
	    ArrayList<MusicAlbum> allALbums = new ArrayList<MusicAlbum>();
	    String sXMLDataFromRequest = rs.getDataFromRequestAsXML(sArtistName); 
        allALbums =  xdp.getAlbumsFromRequest(sXMLDataFromRequest);
	    
		return allALbums;
	}

}

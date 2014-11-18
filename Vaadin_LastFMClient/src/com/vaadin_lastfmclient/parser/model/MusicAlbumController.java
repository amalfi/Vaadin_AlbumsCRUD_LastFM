package com.vaadin_lastfmclient.parser.model;

import java.util.ArrayList;
import java.util.List;

import com.vaadin_lastfmclient.parser.XMLDOMParser;
import com.vaadin_lastfmclient.request.RequestService;

public class MusicAlbumController 
{
	public List<MusicAlbum> db = new ArrayList<MusicAlbum>();
	
	public ArrayList<MusicAlbum> returnAllAlbums(String sArtistName)
	{
		
		XMLDOMParser xdp = new XMLDOMParser();
		RequestService rs = new RequestService();
	    ArrayList<MusicAlbum> allALbums = new ArrayList<MusicAlbum>();
	    String sXMLDataFromRequest = rs.getDataFromRequestAsXML(sArtistName); 
        allALbums =  xdp.getAlbumsFromRequest(sXMLDataFromRequest);
	    
		return allALbums;
	}
	
	public int getCurrentId(List db)
	{
		int currentId;
		currentId = db.size();
		return currentId;
	}

	
	public void addMusicAlbum(MusicAlbum musicAlbum)
	{
		db.add(musicAlbum);
	}
	
	public List<MusicAlbum> saveChanges(MusicAlbum musicAlbum)
	{
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
 			if (musicAlbum.getArtistName().equals(musicAlbum.getAlbumName()) && musicAlbum.getImage().equals(musicAlbum.getId()))
 			{
 				toRemove = ma;
 			}
 		}
		db.remove(toRemove);
 		return db;
	}
	
	public List<MusicAlbum> findAll()
	{
		return this.db;
	}

}

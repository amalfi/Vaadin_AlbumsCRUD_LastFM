package com.vaadin_lastfmclient.parser.model;

import java.util.ArrayList;
import java.util.List;

import com.vaadin_lastfmclient.parser.XMLDOMParser;
import com.vaadin_lastfmclient.request.RequestService;

public class AlbumController 
{
	public ArrayList<Album> returnAllAlbums(String sArtistName)
	{
		
		XMLDOMParser xdp = new XMLDOMParser();
		RequestService rs = new RequestService();
	    ArrayList<Album> allALbums = new ArrayList<Album>();
	    String sXMLDataFromRequest = rs.getDataFromRequestAsXML(sArtistName); 
        allALbums =  xdp.getAlbumsFromRequest(sXMLDataFromRequest);
	    
		return allALbums;
	}
}

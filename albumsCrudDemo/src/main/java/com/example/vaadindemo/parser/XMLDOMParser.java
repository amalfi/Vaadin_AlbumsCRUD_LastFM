package com.example.vaadindemo.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.vaadindemo.domain.MusicAlbum;

/**
 * @author : Marcin Berendt
 * Class which contains function for parsing XML to object, trough analyze of DOM elements
 *
 */
public class XMLDOMParser {

  public ArrayList<MusicAlbum> getAlbumsFromRequest(String sXMLDataFromRequest)
  {
	  ArrayList<MusicAlbum> allAlbumsFromRequest = new ArrayList<MusicAlbum>();	
		   try
		   {   
		   InputStream stream = new ByteArrayInputStream(sXMLDataFromRequest.getBytes(StandardCharsets.UTF_8));

		   DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		   DocumentBuilder db = dbf.newDocumentBuilder();
		   Document doc = db.parse(stream);
		   doc.getDocumentElement().normalize();
		
		    System.out.println("Main DOM element"
		     + doc.getDocumentElement().getNodeName());
		   
		   NodeList nodeLst = doc.getElementsByTagName("album");
		
			    for (int s = 0; s < nodeLst.getLength(); s++) 
			    {
			
			     Node fstNode = nodeLst.item(s);
			
				     if (fstNode.getNodeType() == Node.ELEMENT_NODE)
				     {
					     Element fstElmnt = (Element) fstNode;
					
					     NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("name");
					     Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
					     NodeList fstNm = fstNmElmnt.getChildNodes();
					
					     NodeList stNmElmntLst = fstElmnt.getElementsByTagName("image");
					     Element sstNmElmnt = (Element) stNmElmntLst.item(3); //pobranie elementu 4 w kolejnosci, czyli large
					     NodeList sstNm = sstNmElmnt.getChildNodes();
						
					     NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("url");
					     Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
					     NodeList lstNm = lstNmElmnt.getChildNodes();
					     
					     MusicAlbum currentAlbum = new MusicAlbum();
					     System.out.println("Nazwa albumu : "
					       + ((Node) fstNm.item(0)).getNodeValue());
					     currentAlbum.setAlbumName(String.valueOf(((Node) fstNm.item(0)).getNodeValue()));
					     
					     System.out.println("Okladka : "
					       + ((Node) sstNm.item(0)).getNodeValue());
						 currentAlbum.setImage(String.valueOf(((Node) sstNm.item(0)).getNodeValue()));	
					     
					     allAlbumsFromRequest.add(currentAlbum);
				    }
			   }
		  } 
		   catch (Exception e)
		   {
		   e.printStackTrace();
		   }
		   
	   return allAlbumsFromRequest;
 }
}
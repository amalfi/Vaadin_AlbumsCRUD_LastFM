package com.example.vaadindemo.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Marcin Berendt
 * Simple REST Client to get response from lastFM API
 */
public class RequestService 
{
	public String getDataFromRequestAsXML(String sArtistName)
	{
	String sXML = "";
		try
		{
			String sUrl = "http://ws.audioscrobbler.com/2.0/?method=artist.getTopAlbums&artist=@Artist@&api_key=83d674fdb0f2798509110d0ed6261672";
			sUrl=sUrl.replace("@Artist@", sArtistName);
			URL url = new URL(sUrl);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) 
			{
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			System.out.println("Output from Server .... \n");
			StringBuilder sbXMLFromServer = new StringBuilder();
			while ((output = br.readLine()) != null)
			{
				System.out.println(output);
				sbXMLFromServer.append(output);
			}
			sXML = sbXMLFromServer.toString();
			conn.disconnect();

		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return sXML;
	}
	
}

package com.example.vaadindemo.tools;

import java.util.List;

import com.example.vaadindemo.domain.MusicAlbum;
import com.example.vaadindemo.service.MusicAlbumManager;

public class Tools 
{

	private int getLastIdFromDB()
	{
		MusicAlbumManager pm = new MusicAlbumManager();
		int iLastId = 0;
		List<MusicAlbum> allMusicAlbums = pm.findAll();
		for(MusicAlbum musicAlbum : allMusicAlbums)
		{
			iLastId = musicAlbum.getId();
		}
		if(allMusicAlbums.size()!=0)
		{
			iLastId=iLastId+1;
		}
		
		return iLastId;
	}
	
	public static boolean isNull(Object value)
	{
		boolean isNull=false;
		String nonSelectedRowIdValue = String.valueOf(value);
		if(nonSelectedRowIdValue.equals(null) || nonSelectedRowIdValue.equals("null"))
		{
			isNull=true;
		}
		return isNull;
	}
}

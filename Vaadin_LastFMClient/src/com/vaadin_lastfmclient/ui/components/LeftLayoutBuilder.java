package com.vaadin_lastfmclient.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin_lastfmclient.parser.model.MusicAlbum;
import com.vaadin_lastfmclient.parser.model.MusicAlbumController;

@SuppressWarnings("serial")
public class LeftLayoutBuilder extends AbstractOrderedLayout
{

	public static VerticalLayout returnLeftLayout() {

		VerticalLayout leftLayout = new VerticalLayout();
		
		final TextField tfNameOfArtist = new TextField("Name of Artist");
		final TextField tfNameOfAlbum = new TextField("Name of Album");
		final TextField tfAlbumCoverPath = new TextField("Album cover path");
		final Button addAlbumInfoByHand = new Button("Add album manualy");
		//final Button addAlbumUsingDataFromLastFM = new Button("Add album data from LastFm");
		
		final Table table = new Table("Music Album Informations");

		table.addContainerProperty("Artist Name", String.class, "");
		table.addContainerProperty("Album Name",  String.class, "");
		table.addContainerProperty("Album Cover",  String.class, "");
		
		
		table.setSizeUndefined();
		
		leftLayout.addComponent(tfNameOfArtist);
		leftLayout.addComponent(tfNameOfAlbum);
		leftLayout.addComponent(tfAlbumCoverPath);
		leftLayout.addComponent(table);
		leftLayout.addComponent(addAlbumInfoByHand);
		//leftLayout.addComponent(addAlbumUsingDataFromLastFM);

			addAlbumInfoByHand.addClickListener(new Button.ClickListener() 
			{
				
				@Override
				public void buttonClick(ClickEvent event) 
				{
					MusicAlbumController mac = new MusicAlbumController();
					
					MusicAlbum musicAlbum = new MusicAlbum();
					int currentId = table.size();
				
					musicAlbum.setId(currentId);
					musicAlbum.setArtistName(tfNameOfArtist.getValue());
					musicAlbum.setAlbumName(tfNameOfAlbum.getValue());
					musicAlbum.setImage(tfAlbumCoverPath.getValue());
					
					mac.addMusicAlbum(musicAlbum);
					
					table.addItem(new Object[]
					{
							 musicAlbum.getArtistName(),
							 musicAlbum.getAlbumName(),
							 musicAlbum.getImage()},
							 musicAlbum.getId());
					}
			});
	
		leftLayout.setMargin(true);
		return leftLayout;
	}
	public static VerticalLayout returnLeftLayoutWithContentFromDB() {

		VerticalLayout leftLayout = new VerticalLayout();
		
		final TextField tfNameOfArtist = new TextField("Name of Artist");
		final TextField tfNameOfAlbum = new TextField("Name of Album");
		final TextField tfAlbumCoverPath = new TextField("Album cover path");
		final Button addAlbumInfoByHand = new Button("Add album manualy");
		//final Button addAlbumUsingDataFromLastFM = new Button("Add album data from LastFm");
		
		final Table table = new Table("Music Album Informations");

		table.addContainerProperty("Artist Name", String.class, "");
		table.addContainerProperty("Album Name",  String.class, "");
		table.addContainerProperty("Album Cover",  String.class, "");
		
		
		table.setSizeUndefined();
		
		leftLayout.addComponent(tfNameOfArtist);
		leftLayout.addComponent(tfNameOfAlbum);
		leftLayout.addComponent(tfAlbumCoverPath);
		leftLayout.addComponent(table);
		leftLayout.addComponent(addAlbumInfoByHand);
		//leftLayout.addComponent(addAlbumUsingDataFromLastFM);

			addAlbumInfoByHand.addClickListener(new Button.ClickListener() 
			{
				
				@Override
				public void buttonClick(ClickEvent event) 
				{
					MusicAlbumController mac = new MusicAlbumController();
					
					MusicAlbum musicAlbum = new MusicAlbum();
					int currentId = table.size();
				List<MusicAlbum> db = mac.findAll();
				for(MusicAlbum currentMusicAlbum : db)
				{
					musicAlbum.setId(currentMusicAlbum.getId());
					musicAlbum.setArtistName(currentMusicAlbum.getArtistName());
					musicAlbum.setAlbumName(currentMusicAlbum.getAlbumName());
					musicAlbum.setImage(currentMusicAlbum.getImage());
					
					mac.addMusicAlbum(musicAlbum);
					
					table.addItem(new Object[]
					{
							 musicAlbum.getArtistName(),
							 musicAlbum.getAlbumName(),
							 musicAlbum.getImage()},
							 musicAlbum.getId());
					}
				}
			});
	
		leftLayout.setMargin(true);
		return leftLayout;
	}

	
}

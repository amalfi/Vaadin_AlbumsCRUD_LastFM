package com.vaadin_lastfmclient.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.server.ClassResource;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin_lastfmclient.parser.model.MusicAlbum;
import com.vaadin_lastfmclient.parser.model.MusicAlbumController;

public class MyUIBuilder extends AbstractOrderedLayout
{
	private static final long serialVersionUID = 1816329191331869852L;

	public HorizontalSplitPanel returnLayout()
	{
		final MusicAlbumController ac = new MusicAlbumController();
		final FormLayout formLayout = new FormLayout();
		final HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		
		//--------------------------------------------------------UI Components declaration
		final TextArea taArtistInfo = new TextArea("Album Informations");
		final TextField tfNameOfArtist = new TextField("Name of Artist");
		final NativeSelect nsAlbumDropdownList = new NativeSelect("List of entered artist albums");
		final Button button = new Button("Load albums of this Artist");
		final Button bLoadAlbumInfo = new Button("Load album cover");
		final Button addAlbumInformationToDb= new Button("Add album information to db");
		final Button addAlbumUsingDataFromLastFM = new Button("Add album data from LastFm");
		
		//--------------------------------------------------------Adding Components to Layout
		//VerticalLayout leftLayout = new VerticalLayout();
		
		final VerticalLayout leftLayout = LeftLayoutBuilder.returnLeftLayout();
		final VerticalLayout rightLayout = new VerticalLayout();
		final VerticalLayout rightHigherLayout = new VerticalLayout();
		rightHigherLayout.setMargin(true);
		rightHigherLayout.addComponent(addAlbumUsingDataFromLastFM);
		
		rightHigherLayout.addComponent(rightLayout);
		rightLayout.addComponent(tfNameOfArtist);
		rightLayout.addComponent(button);
		rightLayout.addComponent(nsAlbumDropdownList);
		rightLayout.addComponent(bLoadAlbumInfo);
		rightLayout.addComponent(addAlbumInformationToDb);
		rightLayout.setMargin(true);
		
		horizontalSplitPanel.addComponent(leftLayout);
		horizontalSplitPanel.addComponent(rightHigherLayout);
		
		rightLayout.setVisible(false); //do wywolania z klasy LeftLayoutBuilder pod buttonListenerem przycisku addAlbumUsingDataFromLastFM
	
		//-------------------------------------------------------
		formLayout.setMargin(true);
		addAlbumUsingDataFromLastFM.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				rightLayout.setVisible(true);
			}
		});
		
		button.addClickListener(new Button.ClickListener() 
		{
			private static final long serialVersionUID = 4966144306516781670L;
			public void buttonClick(ClickEvent event) 
			{
				ArrayList<MusicAlbum> albumsInfo = new ArrayList<MusicAlbum>();
				albumsInfo=ac.returnAllAlbums(String.valueOf(tfNameOfArtist.getValue()));

				for(MusicAlbum currentAlbum : albumsInfo)
					{
						nsAlbumDropdownList.addItem(currentAlbum.getAlbumName());
					}
			}
		});
		
		bLoadAlbumInfo.addClickListener(new Button.ClickListener() 
		{
			private static final long serialVersionUID = 4966144306516781670L;
			public void buttonClick(ClickEvent event) 
			{
				ArrayList<MusicAlbum> albumsInfo = new ArrayList<MusicAlbum>();
				String sCurrentValue = nsAlbumDropdownList.getValue().toString();
				
				albumsInfo=ac.returnAllAlbums(String.valueOf(tfNameOfArtist.getValue()));
				for(MusicAlbum currentAlbum : albumsInfo)
				{
					
					if(currentAlbum.getAlbumName().equals(nsAlbumDropdownList.getValue()))
					{
						Image currentAlbumImage = new Image(sCurrentValue, 
								new ExternalResource(currentAlbum.getImage()));
						rightLayout.removeAllComponents();
						rightLayout.addComponent(tfNameOfArtist);
						rightLayout.addComponent(button);
						rightLayout.addComponent(nsAlbumDropdownList);
						rightLayout.addComponent(bLoadAlbumInfo);
						rightLayout.addComponent(currentAlbumImage);
						rightLayout.addComponent(addAlbumInformationToDb);
					}
				}
			}
		});
		addAlbumInformationToDb.addClickListener(new Button.ClickListener() 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) 
			{
				List<MusicAlbum> db = new ArrayList<MusicAlbum>();
				MusicAlbumController musicAlbumController = new MusicAlbumController();
				MusicAlbum musicAlbum = new MusicAlbum();
				
				ArrayList<MusicAlbum> albumsInfo = new ArrayList<MusicAlbum>();
				String sCurrentValue = nsAlbumDropdownList.getValue().toString();
				
				albumsInfo=ac.returnAllAlbums(String.valueOf(tfNameOfArtist.getValue()));
				for(MusicAlbum currentAlbum : albumsInfo)
				{
					
					if(currentAlbum.getAlbumName().equals(nsAlbumDropdownList.getValue()))
					{
						musicAlbum.setAlbumName(currentAlbum.getAlbumName());
						musicAlbum.setArtistName(currentAlbum.getArtistName());
						musicAlbum.setImage(currentAlbum.getImage());
						musicAlbum.setId(musicAlbumController.findAll().size()+1);
					}
				}
				
				musicAlbumController.addMusicAlbum(musicAlbum);
				db=musicAlbumController.findAll();
				leftLayout.removeAllComponents();
				LeftLayoutBuilder.returnLeftLayout();
				
				
				
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
				

				MusicAlbumController mac = new MusicAlbumController();
				
				//MusicAlbum musicAlbum = new MusicAlbum();
				int currentId = table.size();
			//List<MusicAlbum> db = mac.findAll();
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
			
			
			//leftLayout=LeftLayoutBuilder.returnLeftLayoutWithContentFromDB();
		});
		
		return horizontalSplitPanel;
	}

}

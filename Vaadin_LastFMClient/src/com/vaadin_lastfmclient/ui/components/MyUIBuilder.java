package com.vaadin_lastfmclient.ui.components;

import java.util.ArrayList;

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
		//--------------------------------------------------------Adding Components to Layout
		//VerticalLayout leftLayout = new VerticalLayout();
		VerticalLayout leftLayout = LeftLayoutBuilder.returnLeftLayout();
		
		
		final VerticalLayout rightLayout = new VerticalLayout();
		rightLayout.addComponent(tfNameOfArtist);
		rightLayout.addComponent(button);
		rightLayout.addComponent(nsAlbumDropdownList);
		rightLayout.addComponent(bLoadAlbumInfo);
		rightLayout.addComponent(addAlbumInformationToDb);
		rightLayout.setMargin(true);
		
		horizontalSplitPanel.addComponent(leftLayout);
		horizontalSplitPanel.addComponent(rightLayout);
		
		rightLayout.setVisible(false); //do wywolania z klasy LeftLayoutBuilder pod buttonListenerem przycisku addAlbumUsingDataFromLastFM
	
		//-------------------------------------------------------
		formLayout.setMargin(true);
		
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
		
		return horizontalSplitPanel;
	}
}

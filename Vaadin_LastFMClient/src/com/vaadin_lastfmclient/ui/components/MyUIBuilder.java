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
import com.vaadin_lastfmclient.parser.model.Album;
import com.vaadin_lastfmclient.parser.model.AlbumController;

public class MyUIBuilder extends AbstractOrderedLayout
{

	private static final long serialVersionUID = 1816329191331869852L;

	public HorizontalSplitPanel returnLayout()
	{
		final AlbumController ac = new AlbumController();
		final FormLayout formLayout = new FormLayout();
		final HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		
		//--------------------------------------------------------UI Components declaration
		final TextArea taArtistInfo = new TextArea("Album Informations");
		final TextField tfNameOfArtist = new TextField("Name of Artist");
		final NativeSelect nsAlbumDropdownList = new NativeSelect("List of entered artist albums");
		Button button = new Button("Load albums of this Artist");
		final Button bLoadAlbumInfo = new Button("Load album cover");
		//--------------------------------------------------------Adding Components to Layout
		VerticalLayout leftLayout = new VerticalLayout();
		leftLayout.addComponent(tfNameOfArtist);
		leftLayout.addComponent(button);
		leftLayout.setMargin(true);
		
		final VerticalLayout rightLayout = new VerticalLayout();
		rightLayout.addComponent(nsAlbumDropdownList);
		rightLayout.addComponent(bLoadAlbumInfo);

		rightLayout.setMargin(true);
		
		horizontalSplitPanel.addComponent(leftLayout);
		horizontalSplitPanel.addComponent(rightLayout);
	
		//-------------------------------------------------------
		formLayout.setMargin(true);
		
		button.addClickListener(new Button.ClickListener() 
		{
			private static final long serialVersionUID = 4966144306516781670L;
			public void buttonClick(ClickEvent event) 
			{
				ArrayList<Album> albumsInfo = new ArrayList<Album>();
				albumsInfo=ac.returnAllAlbums(String.valueOf(tfNameOfArtist.getValue()));

				for(Album currentAlbum : albumsInfo)
					{
						nsAlbumDropdownList.addItem(currentAlbum.getName());
					}
			}
		});
		
		bLoadAlbumInfo.addClickListener(new Button.ClickListener() 
		{
			private static final long serialVersionUID = 4966144306516781670L;
			public void buttonClick(ClickEvent event) 
			{
				ArrayList<Album> albumsInfo = new ArrayList<Album>();
				String sCurrentValue = nsAlbumDropdownList.getValue().toString();
				
				albumsInfo=ac.returnAllAlbums(String.valueOf(tfNameOfArtist.getValue()));
				for(Album currentAlbum : albumsInfo)
				{
					
					if(currentAlbum.getName().equals(nsAlbumDropdownList.getValue()))
					{
						Image currentAlbumImage = new Image(sCurrentValue, 
								new ExternalResource(currentAlbum.getImage()));
						rightLayout.removeAllComponents();
						rightLayout.addComponent(nsAlbumDropdownList);
						rightLayout.addComponent(bLoadAlbumInfo);
						
						rightLayout.addComponent(currentAlbumImage);
					}
				}
			}
		});
		
		return horizontalSplitPanel;
	}
}

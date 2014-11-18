package com.plusmpm.vaadindemo.ui;

import com.example.vaadindemo.domain.MusicAlbum;
import com.example.vaadindemo.service.MusicAlbumManager;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MyFormWindow extends Window 
{
	private static final long serialVersionUID = 1L;

	public MyFormWindow(final String sMainOperationType, final MusicAlbumManager musicAlbumsManager, final MusicAlbum musicAlbum , BeanItem<MusicAlbum> musicAlbumItem, final BeanItemContainer<MusicAlbum> musicAlbums) 
	{
		setModal(true);
		center();

		final FormLayout form = new FormLayout();
		final FieldGroup binder = new FieldGroup(musicAlbumItem);

		final Button saveChanges = new Button(sMainOperationType);
		final Button cancelBtn = new Button(" Anuluj ");
		final Button getDataFromLastFm= new Button("Get data from lastFm");
		
		form.addComponent(binder.buildAndBind("Nazwa artysty", "artistName"));
		form.addComponent(binder.buildAndBind("Nazwa albumu", "albumName"));
		form.addComponent(binder.buildAndBind("Okladka albumu", "image"));

		//form.addComponent(binder.buildAndBind("Id", "id"));
		
		binder.setBuffered(true);

		binder.getField("artistName").setRequired(true);
		binder.getField("albumName").setRequired(true);
		binder.getField("image").setRequired(true);

		
		VerticalLayout fvl = new VerticalLayout();
		fvl.setMargin(true);
		fvl.addComponent(form);
		
		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(saveChanges);
		hl.addComponent(cancelBtn);
		fvl.addComponent(hl);

		setContent(fvl);

		saveChanges.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try 
				{
					binder.commit();
				} 
				catch (CommitException e)
				{
					e.printStackTrace();
				}
				
				musicAlbum.setArtistName(binder.getField("artistName").getValue().toString());
				musicAlbum.setArtistName(binder.getField("albumName").getValue().toString());
				musicAlbum.setArtistName(binder.getField("image").getValue().toString());
			
				
				if(sMainOperationType.equals("Add"))
				{
					musicAlbumsManager.addMusicAlbum(musicAlbum);
					musicAlbums.addAll(musicAlbumsManager.findAll());
				}
				if(sMainOperationType.equals("Edit"))
				{
					musicAlbumsManager.saveChanges(musicAlbum);
					musicAlbums.removeAllItems();
					musicAlbums.addAll(musicAlbumsManager.findAll());
				}
				if(sMainOperationType.equals("Delete"))
				{
					musicAlbumsManager.deletePerson(musicAlbum);
					musicAlbums.removeAllItems();
					musicAlbums.addAll(musicAlbumsManager.findAll());
				}
				
				close();
			}
		});

		cancelBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				binder.discard();
				close();
			}
		});
	}
}

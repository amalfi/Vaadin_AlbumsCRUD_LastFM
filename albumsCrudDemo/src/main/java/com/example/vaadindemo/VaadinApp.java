package com.example.vaadindemo;

import java.util.ArrayList;
import java.util.List;

import com.example.vaadindemo.domain.MusicAlbum;
import com.example.vaadindemo.service.MusicAlbumManager;
import com.vaadin.annotations.Title;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Title("Vaadin MusicAlbums Crud Application")
public class VaadinApp extends UI {

	private static final long serialVersionUID = 1L;

	private MusicAlbumManager musicAlbumManager = new MusicAlbumManager();
	//private MusicAlbum musicAlbum = new MusicAlbum(getLastIdFromDB(),"Queen","TheBestOf","c:/SomePathToImage");
	private MusicAlbum musicAlbum = new MusicAlbum();
	Table musicAlbumsTable=null;
	private BeanItem<MusicAlbum> musicAlbumItem = new BeanItem<MusicAlbum>(musicAlbum);

	private BeanItemContainer<MusicAlbum> musicAlbums = new BeanItemContainer<MusicAlbum>(
			MusicAlbum.class);

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
	

	@Override
	protected void init(VaadinRequest request) {
		
		final HorizontalSplitPanel horizontalSplitPanel = new HorizontalSplitPanel();
		//final Table musicAlbumsTable = new Table("MusicAlbums", musicAlbums);
		musicAlbumsTable = new Table("MusicAlbums", musicAlbums);
		
		musicAlbumsTable.setColumnHeader("artistName", "Nazwa artysty");
		musicAlbumsTable.setColumnHeader("albumName", "Nazwa albumi");
		musicAlbumsTable.setColumnHeader("image", "Okładka albumu");
		
		Button addMusicAlbumFormBtn = new Button("Add ");
		Button deleteMusicAlbumFormBtn = new Button("Delete");
		Button editMusicAlbumFormBtn = new Button("Edit");
		Button addMusicAlbumFromLastFM = new Button("Add data from Last.fm DB");
		//---------------------------------------------- Begin - Components for right panel
		final TextField tfNameOfArtist = new TextField("Name of Artist");
		final NativeSelect nsAlbumDropdownList = new NativeSelect("List of entered artist albums");
		final Button buttonLoadArtistAlbums = new Button("Load albums of this Artist");
		final Button bLoadAlbumInfo = new Button("Load album cover");
		final Button addAlbumInformationToDb= new Button("Add album information to db");
		//---------------------------------------------- End - Components for right panel
		
		
		VerticalLayout vl = new VerticalLayout();
		final VerticalLayout rightvl = new VerticalLayout();
		vl.setMargin(true);
		rightvl.setMargin(true);
		
		horizontalSplitPanel.addComponent(vl);
		horizontalSplitPanel.addComponent(rightvl);
		
		setContent(horizontalSplitPanel);
		//---------------------------------------------- Begin - Listeners for elements from right panel
		addMusicAlbumFromLastFM.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				rightvl.addComponent(tfNameOfArtist);
				rightvl.addComponent(buttonLoadArtistAlbums);
			}
		});
		
		buttonLoadArtistAlbums.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				//dodanie funkcjonalnosci dodajacej na prawa strone panelu dropdown list z albumami artysty
				MusicAlbumManager mam =  new MusicAlbumManager();
				ArrayList<MusicAlbum> albumsInfo = new ArrayList<MusicAlbum>();
				albumsInfo=mam.returnAllAlbums(String.valueOf(tfNameOfArtist.getValue()));

				for(MusicAlbum currentAlbum : albumsInfo)
					{
						nsAlbumDropdownList.addItem(currentAlbum.getAlbumName());
					}
				rightvl.addComponent(nsAlbumDropdownList);
				rightvl.addComponent(addAlbumInformationToDb);
				rightvl.addComponent(bLoadAlbumInfo);
				//dodanie przycisku 'Show Album
			}
		});
		
		addAlbumInformationToDb.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) 
			{
				MusicAlbumManager mam = new MusicAlbumManager();
				MusicAlbum musicAlbumToAdd =  new MusicAlbum();
				musicAlbumToAdd.setId(0);
				musicAlbumToAdd.setAlbumName(nsAlbumDropdownList.getValue().toString());;
				musicAlbumToAdd.setArtistName(tfNameOfArtist.getValue().toString());;
				musicAlbumToAdd.setImage("path to image");
				
				mam.addMusicAlbum(musicAlbumToAdd);
				musicAlbums.addAll(mam.findAll());
			}
		});
		
		bLoadAlbumInfo.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				ArrayList<MusicAlbum> albumsInfo = new ArrayList<MusicAlbum>();
				String sCurrentValue = nsAlbumDropdownList.getValue().toString();
				MusicAlbumManager mam = new MusicAlbumManager();
				
				albumsInfo=mam.returnAllAlbums(String.valueOf(tfNameOfArtist.getValue()));
				for(MusicAlbum currentAlbum : albumsInfo)
				{
					
					if(currentAlbum.getAlbumName().equals(nsAlbumDropdownList.getValue()))
					{
						Image currentAlbumImage = new Image(sCurrentValue, 
								new ExternalResource(currentAlbum.getImage()));
						rightvl.removeAllComponents();
						rightvl.addComponent(tfNameOfArtist);
						rightvl.addComponent(buttonLoadArtistAlbums);
						rightvl.addComponent(nsAlbumDropdownList);
						rightvl.addComponent(bLoadAlbumInfo);
						rightvl.addComponent(currentAlbumImage);
						rightvl.addComponent(addAlbumInformationToDb);
					}
				}
			}
		});
		
		//---------------------------------------------- End - Listeners for elements from right panel
		
		addMusicAlbumFormBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				addWindow(new MyFormWindow("Add"));
			}
		});
		
		editMusicAlbumFormBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) 
			{
				MusicAlbumManager musicAlbumsManager = new MusicAlbumManager();
				addWindow(new MyFormWindow("Edit"));
			}
		});
		
		deleteMusicAlbumFormBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				addWindow(new MyFormWindow("Delete"));
			}
		});
		

		HorizontalLayout hl = new HorizontalLayout();
		hl.addComponent(addMusicAlbumFormBtn);
		hl.addComponent(editMusicAlbumFormBtn);
		hl.addComponent(deleteMusicAlbumFormBtn);
		hl.addComponent(addMusicAlbumFromLastFM);


		musicAlbumsTable.setSelectable(true);
		musicAlbumsTable.setImmediate(true);

		musicAlbumsTable.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				MusicAlbum selectedPerson = (MusicAlbum) musicAlbumsTable.getValue();
				if (selectedPerson == null) {
					musicAlbum.setAlbumName("");
					musicAlbum.setArtistName("");
					musicAlbum.setImage("");
					musicAlbum.setId(0);
				} 
				else 
				{
					musicAlbum.setAlbumName(musicAlbum.getAlbumName());
					musicAlbum.setArtistName(musicAlbum.getArtistName());
					musicAlbum.setImage(musicAlbum.getImage());
					musicAlbum.setId(musicAlbum.getId());
				}
			}
		});

		vl.addComponent(hl);
		vl.addComponent(musicAlbumsTable);
		
	}
	
	private class MyFormWindow extends Window 
	{
		private static final long serialVersionUID = 1L;

		public MyFormWindow(final String sMainOperationType) {
			setModal(true);
			center();

			Object rowId = musicAlbumsTable.getValue();
			Integer id = (Integer) musicAlbumsTable.getContainerProperty(rowId, "id").getValue();
			String artistName = (String) musicAlbumsTable.getContainerProperty(rowId, "artistName").getValue();
			String albumName = (String) musicAlbumsTable.getContainerProperty(rowId, "albumName").getValue();
			String image = (String) musicAlbumsTable.getContainerProperty(rowId, "image").getValue();
			
			musicAlbum.setArtistName(String.valueOf(id));
			musicAlbum.setArtistName(artistName);
			musicAlbum.setAlbumName(albumName);
			musicAlbum.setImage(image);
			
			final FormLayout form = new FormLayout();
			final FieldGroup binder = new FieldGroup(musicAlbumItem);
  
			final Button saveBtn = new Button(sMainOperationType);
			final Button cancelBtn = new Button(" Anuluj ");

			form.addComponent(binder.buildAndBind("Nazwa artysty", "artistName"));
			form.addComponent(binder.buildAndBind("Nazwa albumu", "albumName"));
			form.addComponent(binder.buildAndBind("Okładka albumu", "image"));

			
			binder.setBuffered(true);

			binder.getField("artistName").setRequired(true);
			binder.getField("albumName").setRequired(true);
			binder.getField("image").setRequired(true);
			
			VerticalLayout fvl = new VerticalLayout();
			fvl.setMargin(true);
			fvl.addComponent(form);
			
			HorizontalLayout hl = new HorizontalLayout();
			hl.addComponent(saveBtn);
			hl.addComponent(cancelBtn);
			fvl.addComponent(hl);

			setContent(fvl);

			saveBtn.addClickListener(new ClickListener() {

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
					MusicAlbumManager mam = new MusicAlbumManager();
					int id = mam.findAll().size();
					if(id!=0)
					{
						id=id+1;
					}
					musicAlbum.setId(id);
					String sArtistName = binder.getField("artistName").getValue().toString();
					musicAlbum.setArtistName(binder.getField("artistName").getValue().toString());
					String sAlbumNam = binder.getField("albumName").getValue().toString();
					musicAlbum.setAlbumName(binder.getField("albumName").getValue().toString());
					String image = binder.getField("image").getValue().toString();
					musicAlbum.setImage(binder.getField("image").getValue().toString());
					
					
					if(sMainOperationType.equals("Add"))
					{
						musicAlbumManager.addMusicAlbum(musicAlbum);
						musicAlbums.addAll(musicAlbumManager.findAll());
					}
					if(sMainOperationType.equals("Edit"))
					{
						musicAlbumManager.saveChanges(musicAlbum);
						musicAlbums.removeAllItems();
						musicAlbums.addAll(musicAlbumManager.findAll());
					}
					if(sMainOperationType.equals("Delete"))
					{
						musicAlbumManager.deletePerson(musicAlbum);
						musicAlbums.removeAllItems();
						musicAlbums.addAll(musicAlbumManager.findAll());
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

}

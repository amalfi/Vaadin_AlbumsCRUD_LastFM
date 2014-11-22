package com.example.vaadindemo;

import java.util.ArrayList;

import com.example.vaadindemo.domain.MusicAlbum;
import com.example.vaadindemo.service.MusicAlbumManager;
import com.example.vaadindemo.tools.Tools;
import com.vaadin.annotations.Title;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Title("Vaadin MusicAlbums Crud Application")
public class VaadinApp extends UI {

	private static final long serialVersionUID = 1L;
	ArrayList<MusicAlbum> albumsInfo = new ArrayList<MusicAlbum>();
	private MusicAlbumManager musicAlbumManager = new MusicAlbumManager();
	//private MusicAlbum musicAlbum = new MusicAlbum(getLastIdFromDB(),"Queen","TheBestOf","c:/SomePathToImage");
	private MusicAlbum musicAlbum = new MusicAlbum();
	Table musicAlbumsTable=null;
	private BeanItem<MusicAlbum> musicAlbumItem = new BeanItem<MusicAlbum>(musicAlbum);

	private BeanItemContainer<MusicAlbum> musicAlbums = new BeanItemContainer<MusicAlbum>(MusicAlbum.class);

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
		Button showMeAlbumInformation = new Button("Show me album summary");
		
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
		
		buttonLoadArtistAlbums.addClickListener(new ClickListener() 
		{
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) 
			{
				//dodanie funkcjonalnosci dodajacej na prawa strone panelu dropdown list z albumami artysty
				String sNameOfArtist = String.valueOf(tfNameOfArtist);
				if(sNameOfArtist.equals(""))
				{
					tfNameOfArtist.setComponentError(new UserError("Please enter name of artist"));
				}
				else
				{
					MusicAlbumManager mam =  new MusicAlbumManager();
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
			}
		});
		
		addAlbumInformationToDb.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) 
			{
				String imagePath = "";
				MusicAlbumManager mam = new MusicAlbumManager();
				MusicAlbum musicAlbumToAdd =  new MusicAlbum();
				musicAlbumToAdd.setId(musicAlbumsTable.size()+1);
				musicAlbumToAdd.setAlbumName(nsAlbumDropdownList.getValue().toString());
				musicAlbumToAdd.setArtistName(tfNameOfArtist.getValue().toString());
				for(MusicAlbum currentMusicAlbum : albumsInfo)
				{
					if(currentMusicAlbum.getAlbumName().equals(musicAlbumToAdd.getAlbumName()))
					{
						imagePath=currentMusicAlbum.getImage();
					}
				}
				musicAlbumToAdd.setImage(imagePath);
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
		showMeAlbumInformation.addClickListener(new ClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event)
			{
				
				MusicAlbum summaryMusicAlbumObject = new MusicAlbum();
				addWindow(new MyFormWindow(summaryMusicAlbumObject));
			
			}
		});
		
		
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
		hl.addComponent(showMeAlbumInformation);

		musicAlbumsTable.setSelectable(true);
		musicAlbumsTable.setImmediate(true);

		musicAlbumsTable.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {

				MusicAlbum selectedPerson = (MusicAlbum) musicAlbumsTable.getValue();
				if (selectedPerson == null)
				{
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

		public MyFormWindow(final String sMainOperationType) 
		{
			setModal(true);
			center();

			String artistName = null;
			String albumName = null;
			String image = null;
			
			int tableSize = musicAlbumsTable.size();
			System.out.println(tableSize);
			if(tableSize!=0)
			{
		    //****Check if is null or empty fill with some example values
			Object rowId = musicAlbumsTable.getValue();
			//Integer id = (Integer) musicAlbumsTable.getContainerProperty(rowId, "id").getValue();
			Integer id=tableSize+1;
			
				if(Tools.isNull(rowId))
				{
					artistName="Nazwa artysty";
					albumName="Nazwa albumu";
					image="Okladka albumu";
				}
				else
				{
					artistName = (String) musicAlbumsTable.getContainerProperty(rowId, "artistName").getValue();
					albumName = (String) musicAlbumsTable.getContainerProperty(rowId, "albumName").getValue();
					image = (String) musicAlbumsTable.getContainerProperty(rowId, "image").getValue();
				}
				musicAlbum.setId(id);
				musicAlbum.setArtistName(artistName);
				musicAlbum.setAlbumName(albumName);
				musicAlbum.setImage(image);
			}
			else
			{
				musicAlbum.setId(0);
				musicAlbum.setArtistName("Artist name");
				musicAlbum.setAlbumName("Album name");
				musicAlbum.setImage("Image");
			}
			
			
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
				public void buttonClick(ClickEvent event)
				{
				MusicAlbum musicAlbum2 = new MusicAlbum();	
					try 
					{
						binder.commit();
					} 
					catch (CommitException e)
					{
						e.printStackTrace();
					}
					int tableSize = musicAlbumsTable.size();

					int id = tableSize;
					if(sMainOperationType.equals("Add")!=true)
					{
						Object rowId = musicAlbumsTable.getValue();
					    id = (Integer) musicAlbumsTable.getContainerProperty(rowId, "id").getValue();
					}
					else
					{
						if(id!=0)
						{
						id=id+1;
						}
					}
					
					System.out.println(tableSize);
					if(tableSize!=0)
					{
					musicAlbum2.setId(id);
					musicAlbum2.setArtistName(binder.getField("artistName").getValue().toString());
					musicAlbum2.setAlbumName(binder.getField("albumName").getValue().toString());
					musicAlbum2.setImage(binder.getField("image").getValue().toString());
					}
					else
					{
					musicAlbum2.setId(id);
					String sArtistName = binder.getField("artistName").getValue().toString();
					musicAlbum2.setArtistName(binder.getField("artistName").getValue().toString());
					String sAlbumNam = binder.getField("albumName").getValue().toString();
					musicAlbum2.setAlbumName(binder.getField("albumName").getValue().toString());
					String image = binder.getField("image").getValue().toString();
					musicAlbum2.setImage(binder.getField("image").getValue().toString());
					}
				
					if(sMainOperationType.equals("Add"))
					{
						musicAlbumManager.addMusicAlbum(musicAlbum2);
						musicAlbums.addAll(musicAlbumManager.findAll());
					}
					if(sMainOperationType.equals("Edit"))
					{
						musicAlbumManager.saveChanges(musicAlbum2);
						musicAlbums.removeAllItems();
						musicAlbums.addAll(musicAlbumManager.findAll());
					}
					if(sMainOperationType.equals("Delete"))
					{
						musicAlbumManager.deletePerson(musicAlbum2);
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
		
		public MyFormWindow(MusicAlbum summaryMusicAlbumObject) 
		{
			setModal(true);
			center();
			VerticalLayout verticalLayout = new VerticalLayout();
			Object rowId = musicAlbumsTable.getValue();
			
			Label lArtistName = new Label((String) musicAlbumsTable.getContainerProperty(rowId, "artistName").getValue());
			Label lAlbumName = new Label((String) musicAlbumsTable.getContainerProperty(rowId, "albumName").getValue());
			
			Image currentAlbumImage = new Image((String) musicAlbumsTable.getContainerProperty(rowId, "albumName").getValue(), 
					new ExternalResource((String) musicAlbumsTable.getContainerProperty(rowId, "image").getValue()));
		
			verticalLayout.addComponent(lArtistName);
			verticalLayout.addComponent(lAlbumName);
			verticalLayout.addComponent(currentAlbumImage);
			
			verticalLayout.setMargin(true);
			
			setContent(verticalLayout);
		}
	}

}

package com.vaadin_lastfmclient.parser.model;
/**
 * @author : Marcin Berendt
 * Simple domain class which contains information about artist album from parsed XML 
 */
public class MusicAlbum 
{
	private int id;
	private String artistName;
	private String albumName;
	private String image; //url to image
	
	/*public MusicAlbum(int lastIdFromDB, String string, String string2,
			String string3) {
		super();
		this.id=id;
		this.artistName = artistName;
		this.albumName = albumName;
		this.image = image;
	}*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String name) {
		this.albumName = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "MusicALbum [id="+id+", artistName=" + artistName + ", albumName=" + albumName
				+ ", image=" + image + "]";
	}
	
}

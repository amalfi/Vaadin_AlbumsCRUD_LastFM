package com.example.vaadindemo.domain;

public class MusicAlbum {
	
	private int id;
	private String artistName;
	private String albumName;
	private String image; //url to image

	
	public MusicAlbum(int id, String artistName, String albumName, String image) {
		super();
		this.id = id;
		this.artistName = artistName;
		this.albumName = albumName;
		this.image = image;
	}

	public MusicAlbum() 
	{
	}
	
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

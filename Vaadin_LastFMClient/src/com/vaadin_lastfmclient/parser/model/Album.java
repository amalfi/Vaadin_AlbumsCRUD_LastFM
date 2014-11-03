package com.vaadin_lastfmclient.parser.model;
/**
 * @author : Marcin Berendt
 * Simple POJO class which contains information about artist album from parsed XML 
 */
public class Album 
{
	private String name;
	private String image;
	private String url;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}

package com.songusoid;


public class Song {

	private String name;
	private String composer;
	private String location;
	private int upvotes;
	
	public Song() {
		
	}
	
	public Song(String name, String composer, String location, int upvotes) {
		this.name = name;
		this.composer = composer;
		this.location = location;
		this.upvotes = upvotes;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComposer() {
		return composer;
	}
	public void setComposer(String composer) {
		this.composer = composer;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getUpvotes() {
		return upvotes;
	}
	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}
	
	
}

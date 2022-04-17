package com.musicMaker.tfritzy.LogInHttpService.DAO;

/**
 * @author Tyler
 * 
 * A DAO that contains the data of a Song.
 *
 */
public class Song {

	private String songName;
	private String composer;
	private int upvotes;
	private String songBody;
	
	public String getSongBody() {
		return songBody;
	}

	public void setSongBody(String songBody) {
		this.songBody = songBody;
	}
	
	public Song() {
		
	}
	
	public Song(String name, String composer, int upvotes, String songBody) {
		this.songName = name;
		this.composer = composer;
		this.upvotes = upvotes;
		this.songBody = songBody;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public int getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}

	
}

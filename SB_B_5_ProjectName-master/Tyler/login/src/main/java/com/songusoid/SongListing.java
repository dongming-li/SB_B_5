package com.songusoid;


import java.util.ArrayList;

public class SongListing {
	private ArrayList<Song> songs;
	private String message;
	
	public ArrayList<Song> getSongs() {
		return songs;
	}
	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}

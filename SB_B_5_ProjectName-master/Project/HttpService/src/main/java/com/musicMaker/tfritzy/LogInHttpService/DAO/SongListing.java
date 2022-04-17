package com.musicMaker.tfritzy.LogInHttpService.DAO;

import java.util.ArrayList;

/**
 * @author Tyler
 * 
 * A DAO that contains a list of songs.
 *
 */
public class SongListing {
	private ArrayList<Song> songs;
	private RequestResult requestResult;
	public ArrayList<Song> getSongs() {
		return songs;
	}
	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}
	public RequestResult getRequestResult() {
		return requestResult;
	}
	public void setRequestResult(RequestResult requestResult) {
		this.requestResult = requestResult;
	}
	
	
	
	
	

}

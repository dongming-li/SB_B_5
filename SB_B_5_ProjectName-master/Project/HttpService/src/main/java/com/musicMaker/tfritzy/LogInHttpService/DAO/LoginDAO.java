package com.musicMaker.tfritzy.LogInHttpService.DAO;

import java.util.ArrayList;

/**
 * @author Tyler
 * 
 * A DAO that contains data that is returned from a successful login.
 *
 */
public class LoginDAO {
	private ArrayList<Song> songs;
	private ArrayList<String> friends;
	private RequestResult requestResult;
	
	public ArrayList<Song> getSongs() {
		return songs;
	}
	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}
	public ArrayList<String> getFriends() {
		return friends;
	}
	public void setFriends(ArrayList<String> friends) {
		this.friends = friends;
	}
	public RequestResult getRequestResult() {
		return requestResult;
	}
	public void setRequestResult(RequestResult requestResult) {
		this.requestResult = requestResult;
	}
}

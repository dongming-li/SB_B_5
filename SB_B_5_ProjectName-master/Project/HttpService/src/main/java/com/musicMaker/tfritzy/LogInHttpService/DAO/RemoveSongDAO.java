package com.musicMaker.tfritzy.LogInHttpService.DAO;

/**
 * @author Tyler
 * 
 * A DAO that contains data needed to remove a song from a user's library.
 *
 */
public class RemoveSongDAO {
	
	private String username;
	private String password;
	private String songName;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	
	

}

package com.musicMaker.tfritzy.LogInHttpService.DAO;

public class VoteDAO {
	private String username;
	private String songName;
	private String password;
	private int upOrDown;

	

	public int getUpOrDown() {
		return upOrDown;
	}
	public void setUpOrDown(int upOrDown) {
		this.upOrDown = upOrDown;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

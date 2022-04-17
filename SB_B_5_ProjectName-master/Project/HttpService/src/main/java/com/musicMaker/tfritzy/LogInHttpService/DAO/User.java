package com.musicMaker.tfritzy.LogInHttpService.DAO;

/**
 * @author Tyler
 * 
 * A DAO with data about a user.
 *
 */
public class User {
	private String username;
	private String password;
	private String addFriend;
	
	
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
	public String getAddFriend() {
		return addFriend;
	}
	public void setAddFriend(String addFriend) {
		this.addFriend = addFriend;
	}

	
	
}

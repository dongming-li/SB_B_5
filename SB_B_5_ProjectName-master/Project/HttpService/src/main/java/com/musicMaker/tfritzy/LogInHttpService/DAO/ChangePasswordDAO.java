package com.musicMaker.tfritzy.LogInHttpService.DAO;

/**
 * @author Tyler
 * 
 * A DAO that contains details needed to change a user's password.
 *
 */
public class ChangePasswordDAO {
	
	String username;
	String oldPassword;
	String newPassword;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	

	

}

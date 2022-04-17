package com.musicMaker.tfritzy.LogInHttpService.DAO;

/**
 * @author Tyler
 * 
 * A DAO that contains data needed to share a song with a user.
 *
 */
public class ShareSongWithUserDAO {
	private String sharedBy;
	private String sharedWith;
	private String songName;
	
	
	public String getSharedBy() {
		return sharedBy;
	}
	public void setSharedBy(String sharedBy) {
		this.sharedBy = sharedBy;
	}
	public String getSharedWith() {
		return sharedWith;
	}
	public void setSharedWith(String sharedWith) {
		this.sharedWith = sharedWith;
	}
	public String getSongName() {
		return songName;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	
	
	
}

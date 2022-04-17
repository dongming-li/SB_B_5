package com.musicMaker.tfritzy.LogInHttpService;

import com.musicMaker.tfritzy.LogInHttpService.DAO.Song;
import com.musicMaker.tfritzy.LogInHttpService.DAO.User;

/**
 * @author Tyler
 * 
 * A class to help check for null values
 *
 */
public class NullChecks {
	
	/**
	 * @param user The object to check for null values
	 * @return whether this object has null values or not.
	 */
	public static boolean userHasNullValues(User user) {
		if (user.getUsername() == null || user.getPassword() == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param song The Song object to check for null values
	 * @return whether or not this object has null values.
	 */
	public static boolean songHasNullValues(Song song) {
		if (song.getComposer() == null || song.getSongBody() == null || song.getSongName() == null) {
			return true;
		}
		return false;
	}

}

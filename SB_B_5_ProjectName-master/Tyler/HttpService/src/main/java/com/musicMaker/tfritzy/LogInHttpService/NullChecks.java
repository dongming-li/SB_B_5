package com.musicMaker.tfritzy.LogInHttpService;

import com.musicMaker.tfritzy.LogInHttpService.DAO.Song;
import com.musicMaker.tfritzy.LogInHttpService.DAO.User;

public class NullChecks {
	
	public static boolean userHasNullValues(User user) {
		if (user.getUsername() == null || user.getPassword() == null) {
			return true;
		}
		return false;
	}
	
	public static boolean songHasNullValues(Song song) {
		if (song.getComposer() == null || song.getSongBody() == null || song.getSongName() == null) {
			return true;
		}
		return false;
	}

}

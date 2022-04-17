package com.musicMaker.tfritzy.LogInHttpService.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.musicMaker.tfritzy.LogInHttpService.DatabaseConnectionManager;
import com.musicMaker.tfritzy.LogInHttpService.UserQueries;
import com.musicMaker.tfritzy.LogInHttpService.SongQueries;
import com.musicMaker.tfritzy.LogInHttpService.DAO.RemoveSongDAO;
import com.musicMaker.tfritzy.LogInHttpService.DAO.RequestResult;
import com.musicMaker.tfritzy.LogInHttpService.DAO.Song;

/**
 * @author Tyler
 * 
 * A controller that removes a song from a user's library.
 *
 */
@Controller
public class RemoveSongController {
	
	@Autowired
	private DatabaseConnectionManager connection;
	
	@RequestMapping(value = "/removeSong", method = RequestMethod.POST)
	public @ResponseBody RequestResult addNewWorker(@RequestBody RemoveSongDAO removeSong) throws SQLException {
		
		RequestResult result = new RequestResult();
		
		if (UserQueries.isLoginCorrect(removeSong.getUsername(), removeSong.getPassword(), connection)) {
			if (SongQueries.doesSongExist(removeSong.getSongName(), removeSong.getUsername(), connection)) {
				String query = "delete from songs where songName = '" + removeSong.getSongName() + "' and composer = '" + removeSong.getUsername() + "'";
				connection.updateStatement(query);
				result.setErrorMessage(removeSong.getSongName() + "was successfully deleted");
				result.setWasSuccess(true);
			} else {
				SongQueries.deleteShareRelation(removeSong.getSongName(), removeSong.getUsername(), connection);
				
				result.setErrorMessage("The song is no longer shared with you.");
				result.setWasSuccess(false);
				
				
			}
			
		}else {
			result.setErrorMessage("Login details were not correct");
			result.setWasSuccess(false);
		}
		
		connection.closeConnections(); 
		return result;
		
		
	}
		
}

package com.musicMaker.tfritzy.LogInHttpService.controllers;

import java.sql.ResultSet;
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
import com.musicMaker.tfritzy.LogInHttpService.DAO.RequestResult;
import com.musicMaker.tfritzy.LogInHttpService.DAO.ShareSongWithUserDAO;

/**
 * @author Tyler
 * 
 * A Controller that shares a given song with a given user.
 *
 */
@Controller
public class ShareSongWithUserController {

	@Autowired
	private DatabaseConnectionManager connection;

	
	@RequestMapping(value = "/shareSongWithUser", method = RequestMethod.POST)
	public @ResponseBody RequestResult addNewWorker(@RequestBody ShareSongWithUserDAO shareInfo) throws SQLException {
		
		RequestResult result = new RequestResult();
		
		
		if (SongQueries.doesSongExist(shareInfo.getSongName(), connection)) {
			
			if (UserQueries.doesUserExist(shareInfo.getSharedBy(), connection)) {
				
				if (UserQueries.doesUserExist(shareInfo.getSharedWith(), connection)) {
					
					if (!wasSongAlreadyShared(shareInfo, connection)) {
						String query = "insert into songShares (songName, sharedWith, sharedBy) values ('" + shareInfo.getSongName() + "','" + shareInfo.getSharedWith() + "','" + shareInfo.getSharedBy() + "')";
						connection.updateStatement(query);
						connection.closeConnections();
						
						result.setErrorMessage("The song " + shareInfo.getSongName() + " was added to your library");
						result.setWasSuccess(true);
					} else {
						result.setErrorMessage("The song " + shareInfo.getSongName() + " was already in your library");
						result.setWasSuccess(false);
					}
				} else {
					result.setErrorMessage("User the song is being shared with doesn't exist");
					result.setWasSuccess(false);
				}
			} else {
				result.setErrorMessage("User " + shareInfo.getSharedBy() + " sharing song doesn't exist");
				result.setWasSuccess(false);
				
			}
			
		} else {
			result.setErrorMessage("Song to be shared doesn't exist");
			result.setWasSuccess(false);
		}
		
		return result;
	}
	
	private static boolean wasSongAlreadyShared(ShareSongWithUserDAO shareInfo, DatabaseConnectionManager connection) throws SQLException {
		String query = "Select * from songShares where songName = '" + shareInfo.getSongName() + "' and sharedBy = '" + shareInfo.getSharedBy() + "' and sharedWith = '" + shareInfo.getSharedWith() + "'";
		ResultSet rs = connection.queryStatement(query);
		if (rs.next()) {
			connection.closeConnections();
			return true;
		}
		connection.closeConnections();
		return false;
	}
	
		
	
}

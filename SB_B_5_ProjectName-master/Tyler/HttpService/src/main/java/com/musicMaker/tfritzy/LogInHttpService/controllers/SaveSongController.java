package com.musicMaker.tfritzy.LogInHttpService.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.musicMaker.tfritzy.LogInHttpService.DatabaseConnectionManager;
import com.musicMaker.tfritzy.LogInHttpService.SongQueries;
import com.musicMaker.tfritzy.LogInHttpService.DAO.RequestResult;
import com.musicMaker.tfritzy.LogInHttpService.DAO.Song;

@Controller
public class SaveSongController {
	
	@Autowired
	private DatabaseConnectionManager connection;
	
	@RequestMapping(value = "/saveSong", method = RequestMethod.POST)
	public @ResponseBody RequestResult addNewWorker(@RequestBody Song song) throws SQLException {
		
		RequestResult result = new RequestResult();
		
		if (song.getComposer() == null || song.getSongName() == null || song.getSongBody() == null) {
			result.setErrorMessage("You cannot pass in null values");
			result.setWasSuccess(false);
			return result;
		}
		
		if (!SongQueries.doesSongExist(song.getSongName(), song.getComposer(), connection)) {

			String query = createSongQuery(song.getSongName(), song.getComposer(), false, song.getSongBody());
			
			connection.updateStatement(query);
			connection.closeConnections();
			result.setErrorMessage("Song added to the database.");
			result.setWasSuccess(true);
			
			return result;
		} else {
			String query = updateSongQuery(song.getSongName(), song.getComposer(), song.getSongBody());
			System.out.println(query);
			connection.updateStatement(query);
			connection.closeConnections();
			result.setErrorMessage("Old Songbody updated.");
			result.setWasSuccess(true);
			return result;
		}
	}
	

	
	private String createSongQuery(String name, String composer, boolean sharedGlobaly, String songBody) {
		return "insert into songs (songName, composer, upvotes, sharedGlobaly, songBody)"
				+ " values('" + name + "','" + composer + "', 1 , " + sharedGlobaly + " , '" + songBody + "')";
	}
	
	private String updateSongQuery(String name, String composer, String songBody) {
		return "update songs set songBody = '" + songBody + "' where songName = '" + name + "' and composer = '" + composer + "'";
	}
	
}

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
import com.musicMaker.tfritzy.LogInHttpService.DAO.RequestResult;
import com.musicMaker.tfritzy.LogInHttpService.DAO.Song;

/**
 * @author Tyler
 * 
 * A controller that adds a song to the global listing of songs.
 *
 */
@Controller
public class ShareSongController {

	@Autowired
	private DatabaseConnectionManager connection;

	@RequestMapping(value = "/shareSong", method = RequestMethod.POST)
	public @ResponseBody RequestResult addNewWorker(@RequestBody Song song) throws SQLException {
		
		RequestResult result = new RequestResult();
		
		if (SongQueries.doesSongExist(song.getSongName(), song.getComposer(), connection)) {
			String query = "update songs set sharedGlobaly = true where songName = '" + song.getSongName() + "'";
			connection.updateStatement(query);
			result = new RequestResult("The song was shared.", true);
			return result;
		}
		return new RequestResult("The song could not be shared.", false);
	}
}

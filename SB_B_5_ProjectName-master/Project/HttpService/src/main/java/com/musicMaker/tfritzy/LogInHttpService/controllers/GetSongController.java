package com.musicMaker.tfritzy.LogInHttpService.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.musicMaker.tfritzy.LogInHttpService.DatabaseConnectionManager;
import com.musicMaker.tfritzy.LogInHttpService.DAO.RequestResult;
import com.musicMaker.tfritzy.LogInHttpService.DAO.Song;
import com.musicMaker.tfritzy.LogInHttpService.DAO.SongListing;

/**
 * @author Tyler
 * 
 * A controller that retrieves the details about a song given a song name.
 *
 */
@Controller
public class GetSongController {
	
	@Autowired
	DatabaseConnectionManager connection;
	
	@RequestMapping(value = "/getSong" , method = RequestMethod.POST)
	public @ResponseBody Song addNewWorker(@RequestBody Song song) throws SQLException {
		
		Song retSong = new Song();
		String query = "select * from songs where songName = '" + song.getSongName() + "'";
		ResultSet rs = connection.queryStatement(query);
		
		if (rs.next()) {
			retSong.setSongName(rs.getString("songName"));
			retSong.setComposer(rs.getString("composer"));
			retSong.setUpvotes(rs.getInt("upvotes"));
			retSong.setSongBody(rs.getString("songBody"));
		}

		return retSong;
		
		
	}
}

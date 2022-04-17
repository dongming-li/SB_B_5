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
import com.musicMaker.tfritzy.LogInHttpService.DAO.GlobalShareDAO;
import com.musicMaker.tfritzy.LogInHttpService.DAO.Song;
import com.musicMaker.tfritzy.LogInHttpService.DAO.SongListing;

/**
 * @author Tyler
 * 
 * A controller that gets a list of all songs shared globally on the database.
 *
 */
@Controller
public class GetGlobalSongs {

	
	@Autowired
	private DatabaseConnectionManager connection;

	@RequestMapping(value = "/getGlobalSongs", method = RequestMethod.POST)
	public @ResponseBody SongListing addNewWorker(@RequestBody GlobalShareDAO globalShareDAO) throws SQLException {
		
		String query = "select * from songs where sharedGlobaly = true order by upvotes desc";
		ResultSet rs = connection.queryStatement(query);
		ArrayList<Song> songs = new ArrayList<Song>();
		SongListing retSongs = new SongListing();
		int counter = 0;
		
		while(rs.next() && counter < (globalShareDAO.getPage() + 1)*15) {
			if (counter >= globalShareDAO.getPage()*15){
				String name = rs.getString("songName");
				String songComposer = rs.getString("composer");
				int upvotes = rs.getInt("upvotes");
				String songBody = rs.getString("songBody");
				songs.add(new Song(name, songComposer, upvotes, songBody));
			}

			counter += 1;
		}
		retSongs.setSongs(songs);
		retSongs.setRequestResult(null);
		rs.close();
		connection.closeConnections();
		return retSongs;
	}
		
}

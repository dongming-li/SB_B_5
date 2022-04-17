package com.songusoid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.sql.*;
import java.util.ArrayList;


@Controller
public class LoginController {
	
	@Autowired
	private Connection connection;

	@RequestMapping(value = "/Login" , method = RequestMethod.POST)
	public @ResponseBody SongListing addNewWorker(@RequestBody User user) throws SQLException {
		
		if (!doesAccountExist(user.getUsername(), user.getPassword())) {
			SongListing songListing = new SongListing();
			songListing.setMessage("invalid Login");
			return songListing;
		} else {
			Statement stmt=connection.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from song where composer = '" + user.getUsername() + "'");  
			SongListing songListing = new SongListing();
			ArrayList<Song> songs = new ArrayList<Song>();
			while(rs.next()) {
				String name = rs.getString("name");
				String composer = rs.getString("composer");
				String location = rs.getString("location");
				int upvotes = rs.getInt("upvotes");
				songs.add(new Song(name, composer, location, upvotes));
				
			}
			songListing.setSongs(songs);
			songListing.setMessage("Here are the songs!");
			return songListing;
		}  

		
	}
	
	
	private boolean doesAccountExist(String username, String password) throws SQLException {
		
		Statement stmt=connection.createStatement();  
		ResultSet rs=stmt.executeQuery("Select * from users u where username = '" + username + "' and 'password=" + password + "'");  
		if (rs.next()) {
			return true;
		}
		
		return false;
	
	}
}

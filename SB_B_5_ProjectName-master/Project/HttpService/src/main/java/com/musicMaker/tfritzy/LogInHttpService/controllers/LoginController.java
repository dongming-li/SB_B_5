package com.musicMaker.tfritzy.LogInHttpService.controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.musicMaker.tfritzy.LogInHttpService.DatabaseConnectionManager;
import com.musicMaker.tfritzy.LogInHttpService.FriendQueries;
import com.musicMaker.tfritzy.LogInHttpService.UserQueries;
import com.musicMaker.tfritzy.LogInHttpService.SongQueries;
import com.musicMaker.tfritzy.LogInHttpService.DAO.LoginDAO;
import com.musicMaker.tfritzy.LogInHttpService.DAO.RequestResult;
import com.musicMaker.tfritzy.LogInHttpService.DAO.Song;
import com.musicMaker.tfritzy.LogInHttpService.DAO.User;


/**
 * @author Tyler
 * 
 * A controller that returns a listing of songs and friends if a login is successful.
 *
 */
@Controller
public class LoginController {
	
	@Autowired
	DatabaseConnectionManager connection;

	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	public @ResponseBody LoginDAO addNewWorker(@RequestBody User user) throws SQLException {

		LoginDAO response = new LoginDAO();

		if (!UserQueries.isLoginCorrect(user.getUsername(), user.getPassword(), connection)) {
			response.setRequestResult(new RequestResult(user.getUsername() + " does not exist.", false));
			return response;
		} else {
			ArrayList<Song> songs = SongQueries.getSongsCreatedByUser(user.getUsername(), connection);
			songs.addAll(SongQueries.getSongsSharedWithUser(user.getUsername(), connection));
			response.setSongs(songs);
			response.setRequestResult(new RequestResult("None. Here are the songs and friends", true));

			ArrayList<String> friends = FriendQueries.getFriends(user.getUsername(), connection);
			response.setFriends(friends);
			connection.closeConnections();
			return response;
		} 
	}
	

}

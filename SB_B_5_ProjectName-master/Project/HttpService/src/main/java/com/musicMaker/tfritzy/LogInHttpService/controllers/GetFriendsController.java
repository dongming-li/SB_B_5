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
import com.musicMaker.tfritzy.LogInHttpService.FriendQueries;
import com.musicMaker.tfritzy.LogInHttpService.DAO.PeopleListing;
import com.musicMaker.tfritzy.LogInHttpService.DAO.User;

/**
 * @author Tyler
 * 
 * A controller that retrieves a listing of friends of a given user.
 *
 */
@Controller
public class GetFriendsController {

	@Autowired
	DatabaseConnectionManager connection;

	@RequestMapping(value = "/getFriends" , method = RequestMethod.POST)
	public @ResponseBody PeopleListing addNewWorker(@RequestBody User user) throws SQLException {
		PeopleListing people = new PeopleListing();
		people.setPeople(FriendQueries.getFriends(user.getUsername(), connection));
		people.setMessage("Here's a listing of this user's friends");
		people.setWasSuccess(true);
		return people;
	}
	
}

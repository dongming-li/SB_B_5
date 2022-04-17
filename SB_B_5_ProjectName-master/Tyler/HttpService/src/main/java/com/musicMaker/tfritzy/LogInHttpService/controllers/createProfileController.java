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
import com.musicMaker.tfritzy.LogInHttpService.NullChecks;
import com.musicMaker.tfritzy.LogInHttpService.Queries;
import com.musicMaker.tfritzy.LogInHttpService.DAO.RequestResult;
import com.musicMaker.tfritzy.LogInHttpService.DAO.User;

@Controller
public class createProfileController {
	
	@Autowired
	private DatabaseConnectionManager connection;
	
	
	@RequestMapping(value = "/createProfile" , method = RequestMethod.POST)
	public @ResponseBody RequestResult addNewWorker(@RequestBody User user) throws SQLException{
		
		RequestResult result;
		
		if (NullChecks.userHasNullValues(user)) {
			result = new RequestResult("User cannot have null values", false);
			return result;
		}
		
		if (!Queries.doesUserExist(user.getUsername(), connection)){
			String query = "insert into users (username, password) values('" + user.getUsername() + "','" + user.getPassword() + "')";
			connection.updateStatement(query);
			connection.closeConnections();
			
			result = new RequestResult("None", true);
			return result;
		} else {
			result = new  RequestResult("Username already in use.", false);
			return result;
		}
	}
	
}

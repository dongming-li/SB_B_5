package com.musicMaker.tfritzy.LogInHttpService.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.musicMaker.tfritzy.LogInHttpService.DatabaseConnectionManager;
import com.musicMaker.tfritzy.LogInHttpService.DAO.ChangePasswordDAO;
import com.musicMaker.tfritzy.LogInHttpService.DAO.RequestResult;

@Controller
public class ChangePasswordController {
	
	@Autowired
	DatabaseConnectionManager connection;

	@RequestMapping(value = "/changePassword" , method = RequestMethod.POST)
	public @ResponseBody RequestResult addNewWorker(@RequestBody ChangePasswordDAO userInfo) throws SQLException {
		
		String query = "Update users set password = '" + userInfo.getNewPassword() + "' where username = '" + userInfo.getUsername() + "' and password = '" + userInfo.getOldPassword() + "'";
		System.out.println(query);
		connection.updateStatement(query);
		
		RequestResult result = new RequestResult("None", true);
		return result;
		
	}

}

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
import com.musicMaker.tfritzy.LogInHttpService.DAO.RequestResult;
import com.musicMaker.tfritzy.LogInHttpService.DAO.User;

/**
 * @author Tyler
 * 
 * A controller that allows for adding friend relations
 *
 */
@Controller
public class AddFriendController {
	
	@Autowired
	private DatabaseConnectionManager connection;
	
	/**
	 * @param user The user object that has the details for the friend relation
	 * @return A result Object that contains values about the successfullness of the operation.
	 * @throws SQLException
	 */
	@RequestMapping(value = "/addFriend", method = RequestMethod.POST)
	public @ResponseBody RequestResult addNewWorker(@RequestBody User user) throws SQLException {
		
		RequestResult result;
		
		if (user.getAddFriend() == null) {
			result = new RequestResult("Freind to be added is null", false);
			return result;
		}
		
		if (!UserQueries.isLoginCorrect(user.getUsername(), user.getPassword(), connection)) {
			result = new RequestResult(user.getUsername() + " is not a user or the password was incorrect", false);
			return result;
		} else {
			
			if (UserQueries.doesUserExist(user.getAddFriend(), connection)) {
				
			if (!UserQueries.doesRelationshipAlreadyExist(user.getUsername(), user.getAddFriend(), connection)) {
					String query = "insert into friendRelation (friendOneName, friendTwoName)"
							+ "values('" + user.getUsername() + "','" + user.getAddFriend() + "')";
					connection.updateStatement(query);
					connection.closeConnections();
					
					query = "insert into friendRelation (friendTwoName, friendOneName)"
							+ "values('" + user.getUsername() + "','" + user.getAddFriend() + "')";
					connection.updateStatement(query);
					connection.closeConnections();
					result = new RequestResult("None", true);

				
				} else {
					result = new RequestResult("Friendship already existed!", false);
				}
			} else {
				result = new RequestResult("Friend to be added doesn't exist", false);
			}
		}
		return result;
	}
	
	

	
	

}

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
import com.musicMaker.tfritzy.LogInHttpService.DAO.RequestResult;
import com.musicMaker.tfritzy.LogInHttpService.DAO.VoteDAO;

/**
 * @author Tyler
 * 
 * A controller that allows for the upvoting or downvoting of a song.
 *
 */
@Controller
public class VoteController {
	
	@Autowired
	private DatabaseConnectionManager connection;

	@RequestMapping(value = "/vote", method = RequestMethod.POST)
	public @ResponseBody RequestResult addNewWorker(@RequestBody VoteDAO vote) throws SQLException {
		
		RequestResult result = new RequestResult();
		
		if (vote.getPassword() == null || vote.getSongName() == null || vote.getUpOrDown() == 0 || vote.getUsername() == null) {
			result.setErrorMessage("You gave me some null values that I can't work with :(");
			result.setWasSuccess(false);
			return result;
		}
		

		
		if (UserQueries.isLoginCorrect(vote.getUsername(), vote.getPassword(), connection)){
			
			if (vote.getUpOrDown() == 1) {
				String query = "Update songs set upvotes = upvotes + 1 where songName = '" + vote.getSongName() + "'";
				System.out.println(query);
				connection.updateStatement(query);
				result.setWasSuccess(true);
				result.setErrorMessage("Song successfully upvoted");
			}
			else if (vote.getUpOrDown() == -1) {
				String query = "Update songs set upvotes = upvotes - 1 where songName = '" + vote.getSongName() + "'";
				System.out.println(query);
				connection.updateStatement(query);
				result.setWasSuccess(true);
				result.setErrorMessage("Song successfully downvoted");
			} else {
				result.setErrorMessage("upOrDownVote must be 1 to upvote and -1 to downvote. Other values have no effect");
				result.setWasSuccess(false);
			}
			
		} else {
			result.setErrorMessage("Login details provided were not valid.");
			result.setWasSuccess(false);
		}
			
		connection.closeConnections();
		return result;

	}
		
	
	
}

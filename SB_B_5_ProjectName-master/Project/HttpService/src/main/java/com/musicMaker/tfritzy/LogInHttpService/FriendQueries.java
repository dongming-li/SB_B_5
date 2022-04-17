package com.musicMaker.tfritzy.LogInHttpService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Tyler
 * 
 * class that contains querys that relate to friendships
 *
 */
public class FriendQueries {
	
	/**
	 * Gets a listing of frinds of the specified user
	 * 
	 * @param username The user to get the friends of
	 * @param connection The connection to the database
	 * @return An arraylist of friend names
	 * @throws SQLException
	 */
	public static ArrayList<String> getFriends(String username, DatabaseConnectionManager connection) throws SQLException{
		ArrayList<String> friends = new ArrayList<String>();
		String query = "select friendTwoName from friendRelation where friendOneName = '" + username + "'";
		ResultSet rs = connection.queryStatement(query);
		while (rs.next()) {
			friends.add(rs.getString("friendTwoName"));
		}
		return friends;
	}

}

package com.musicMaker.tfritzy.LogInHttpService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FriendQueries {
	
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

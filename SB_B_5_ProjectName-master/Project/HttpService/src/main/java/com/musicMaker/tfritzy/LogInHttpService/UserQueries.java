package com.musicMaker.tfritzy.LogInHttpService;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Tyler
 * 
 * A class that contains queries that relate to users and Logins
 *
 */
@Component
public class UserQueries {

	/**
	 * Checks if the given user exists in the database
	 * 
	 * @param username The user to look for
	 * @param connection The database connection
	 * @return Whether or not the user exists
	 * @throws SQLException
	 */
	public static boolean doesUserExist(String username, DatabaseConnectionManager connection) throws SQLException {
		String query = "select * from users where username = '" + username + "'";
		ResultSet rs = connection.queryStatement(query);
		if (rs.next()) {
			connection.closeConnections();
			return true;
		} else {
			connection.closeConnections();
			return false;
		}
	}
	
	/**
	 * Checks if the given login details are correct.
	 * 
	 * @param username The username to validate.
	 * @param password THe password to validate.
	 * @param connection The connection with the database
	 * @return True if the login is correct. False if it is not.
	 * @throws SQLException
	 */
	public static boolean isLoginCorrect(String username,String password, DatabaseConnectionManager connection) throws SQLException {
		String query = "select * from users where username = '" + username + "' and password = '" + password + "'";
		System.out.println(query);
		ResultSet rs = connection.queryStatement(query);
		if (rs.next()) {
			connection.closeConnections();
			return true;
		} else {
			connection.closeConnections();
			return false;
		}
	}
	
	/**
	 * Checks if a friendship exists between two users
	 * 
	 * @param friendOne User one.
	 * @param friendTwo User two
	 * @param connection The database connection.
	 * @return True if a friendship exists. False if one does not.
	 * @throws SQLException
	 */
	public static boolean doesRelationshipAlreadyExist(String friendOne, String friendTwo, DatabaseConnectionManager connection) throws SQLException {
		String query = "select * from friendRelation where friendOneName = '" + friendOne + "' and friendTwoName = '" + friendTwo + "'";
		ResultSet rs = connection.queryStatement(query);
		if (rs.next()) {
			connection.closeConnections();
			return true;
		} else {
			connection.closeConnections();
			return false;
		}
	}
	
}

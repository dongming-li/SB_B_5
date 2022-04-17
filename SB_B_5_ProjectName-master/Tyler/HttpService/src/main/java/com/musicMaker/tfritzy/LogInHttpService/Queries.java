package com.musicMaker.tfritzy.LogInHttpService;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Queries {

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
	
	public static boolean isLoginCorrect(String username,String password, DatabaseConnectionManager connection) throws SQLException {
		String query = "select * from users where username = '" + username + "' and password = '" + password + "'";
		ResultSet rs = connection.queryStatement(query);
		if (rs.next()) {
			connection.closeConnections();
			return true;
		} else {
			connection.closeConnections();
			return false;
		}
	}
	
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

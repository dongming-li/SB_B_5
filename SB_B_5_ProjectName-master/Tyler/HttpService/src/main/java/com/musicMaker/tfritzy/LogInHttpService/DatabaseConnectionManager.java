package com.musicMaker.tfritzy.LogInHttpService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionManager {

	private Connection dbConnection;
	private Statement statement;

	private void createConnection() throws SQLException {
		dbConnection = DriverManager.getConnection("jdbc:mysql://mysql.cs.iastate.edu/db309sbb5", "dbu309sbb5",
				"5qQ#ewWx");
	}
	
	public ResultSet queryStatement(String query) throws SQLException {
		createConnection();
		statement=dbConnection.createStatement();  
		ResultSet rs=statement.executeQuery(query);  
		return rs;
	}
	
	public int updateStatement(String query) throws SQLException {
		createConnection();
		statement=dbConnection.createStatement();  
		int result = statement.executeUpdate(query);
		return result;
	}
	
	public void closeConnections() throws SQLException {
		if (dbConnection != null) {
			dbConnection.close();
		}
		if (statement != null) {
			statement.close();
			
		}
		
	}
	
	

}

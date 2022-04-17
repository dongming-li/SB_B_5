package com.musicMaker.tfritzy.LogInHttpService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * @author Tyler Fritz
 * 
 * A class to manage connections with the database.
 *
 */
public class DatabaseConnectionManager {

	private Connection dbConnection;
	private Statement statement;

	
	private void createConnection() throws SQLException {
		dbConnection = DriverManager.getConnection("jdbc:mysql://mysql.cs.iastate.edu/db309sbb5", "dbu309sbb5",
				"5qQ#ewWx");
	}
	
	/**
	 * @param query The query to be executed
	 * @return The results of the query.
	 * @throws SQLException
	 */
	public ResultSet queryStatement(String query) throws SQLException {
		createConnection();
		statement=dbConnection.createStatement();  
		ResultSet rs=statement.executeQuery(query);  
		return rs;
	}
	
	/**
	 * @param query the query to be executed
	 * @return either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
	 * @throws SQLException
	 */
	public int updateStatement(String query) throws SQLException {
		createConnection();
		statement=dbConnection.createStatement();  
		int result = statement.executeUpdate(query);
		return result;
	}
	
	
	/**
	 * 
	 * Shuts down connection to the database.
	 * 
	 * @throws SQLException
	 */
	public void closeConnections() throws SQLException {
		if (dbConnection != null) {
			dbConnection.close();
		}
		if (statement != null) {
			statement.close();
			
		}
		
	}
	
	

}

package com.songusoid;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	public User getUser() {
		return new User();
	}
	
	@Bean
	@Autowired
	public Connection getDatabaseConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(
                "jdbc:mysql://mysql.cs.iastate.edu/db309sbb5",
                "dbu309sbb5","5qQ#ewWx");
		return connection;
	
	}
	
}

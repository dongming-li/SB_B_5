package com.musicMaker.tfritzy.LogInHttpService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.musicMaker.tfritzy.LogInHttpService.DAO.User;

@Configuration
public class Config {

	public User getUser() {
		return new User();
	}
	
	@Bean
	public static DatabaseConnectionManager getDatabaseConnection() throws SQLException {
		return new DatabaseConnectionManager();
	}

	
}

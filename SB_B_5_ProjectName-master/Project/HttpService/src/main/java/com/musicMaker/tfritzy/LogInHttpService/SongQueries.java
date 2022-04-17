package com.musicMaker.tfritzy.LogInHttpService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.musicMaker.tfritzy.LogInHttpService.DAO.Song;

public class SongQueries {

	public static ArrayList<Song> getSongsCreatedByUser(String composer, DatabaseConnectionManager connection) throws SQLException{
		String query = "select * from songs where composer = '" + composer + "'";
		ResultSet rs= connection.queryStatement(query);
		ArrayList<Song> songs = new ArrayList<Song>();
		while(rs.next()) {
			String name = rs.getString("songName");
			String songComposer = rs.getString("composer");
			int upvotes = rs.getInt("upvotes");
			String songBody = rs.getString("songBody");
			songs.add(new Song(name, composer, upvotes, songBody));
		}
		
		connection.closeConnections();
		rs.close();
		return songs;
	}
	
	
	public static ArrayList<Song> getSongsSharedWithUser(String username, DatabaseConnectionManager connection) throws SQLException{
		
		String query = "select * from songs s inner join songShares ss on s.songName = ss.songName where ss.sharedWith = '" + username + "'";
		ResultSet rs = connection.queryStatement(query);
		ArrayList<Song> songs = new ArrayList<Song>();
		
		while(rs.next()) {
			String name = rs.getString("songName");
			String songComposer = rs.getString("composer");
			int upvotes = rs.getInt("upvotes");
			String songBody = rs.getString("songBody");
			songs.add(new Song(name, songComposer, upvotes, songBody));
		}
		rs.close();
		connection.closeConnections();
		return songs;
	}
	
	public static boolean doesSongExist(String songName, String composer, DatabaseConnectionManager connection) throws SQLException {
		
		String query = "Select * from songs where songName = '" + songName + "' and composer = '" + composer + "'";
		System.out.println(query);
		ResultSet rs = connection.queryStatement(query);
		
		if (rs.next()) {
			rs.close();
			connection.closeConnections();
			return true;
		}
		rs.close();
		connection.closeConnections();
		return false;
	}
	
	public static boolean doesSongExist(String songName, DatabaseConnectionManager connection) throws SQLException {
		
		String query = "Select * from songs where songName = '" + songName + "'";
		ResultSet rs = connection.queryStatement(query);
		
		if (rs.next()) {
			rs.close();
			connection.closeConnections();
			return true;
		}
		rs.close();
		connection.closeConnections();
		return false;
	}
	
	public static void deleteShareRelation(String songName, String sharedWith, DatabaseConnectionManager connection) throws SQLException{
		String query = "DELETE FROM songShares where songName = '" + songName + "' and sharedWith = '" + sharedWith + "'";
		connection.updateStatement(query);
		connection.closeConnections();
		
	}
	
	
	
}

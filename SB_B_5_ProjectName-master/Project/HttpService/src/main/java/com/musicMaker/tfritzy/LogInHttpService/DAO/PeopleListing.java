package com.musicMaker.tfritzy.LogInHttpService.DAO;

import java.util.ArrayList;

/**
 * @author Tyler
 * 
 * A DAO that contains data returned when asking for a list of friends.
 *
 */
public class PeopleListing {
	private ArrayList<String> people;
	private String errorMessage;
	private boolean wasSuccess;

	public ArrayList<String> getPeople() {
		return people;
	}

	public void setPeople(ArrayList<String> people) {
		this.people = people;
	}

	public String getMessage() {
		return errorMessage;
	}

	public void setMessage(String message) {
		this.errorMessage = message;
	}

	public boolean wasSuccess() {
		return wasSuccess;
	}

	public void setWasSuccess(boolean wasSuccess) {
		this.wasSuccess = wasSuccess;
	}


	
	
	
}

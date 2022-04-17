package com.musicMaker.tfritzy.LogInHttpService.DAO;

public class RequestResult {
	private boolean wasSuccess;
	private String errorMessage;
	
	public RequestResult() {
		
	}
	
	public RequestResult(String errorMessage, boolean wasSuccess) {
		this.errorMessage = errorMessage;
		this.wasSuccess = wasSuccess;
	}
	
	public boolean isWasSuccess() {
		return wasSuccess;
	}
	public void setWasSuccess(boolean wasSuccess) {
		this.wasSuccess = wasSuccess;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}

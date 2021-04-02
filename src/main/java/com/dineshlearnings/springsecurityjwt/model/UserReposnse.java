package com.dineshlearnings.springsecurityjwt.model;

public class UserReposnse {
	private String token;
	private String message;

	public UserReposnse() {

	}

	public UserReposnse(String token, String message) {
		this.token = token;
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

package com.revature.jwt;

public class AuthenticationResponse {
	
	private final String webToken;

	
	
	public AuthenticationResponse(String webToken) {
		this.webToken = webToken;
	}
	
	public String getWebToken() {
		return webToken;
	}
	
	

}

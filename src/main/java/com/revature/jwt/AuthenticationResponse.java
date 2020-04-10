package com.revature.jwt;


/**
 * A wrapper class that simply contains a single String attribute with a getter and setter, and this is the web token.
 * @author Mason King
 *
 */
public class AuthenticationResponse {
	
	private final String webToken;

	
	
	public AuthenticationResponse(String webToken) {
		this.webToken = webToken;
	}
	
	public String getWebToken() {
		return webToken;
	}
	
	

}

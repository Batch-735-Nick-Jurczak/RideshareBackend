package com.revature.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.revature.beans.ApplicationUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtility {

	private String SECRET_KEY = "ridesharesecretkey";
	
	
	/**
	 * 
	 * @param applicationUser
	 * @return A new JWT 
	 * 
	 * This method takes in an ApplicationUser (Which implements the Spring Security User Class) and calls the create token method with 
	 * the user's username.
	 */
	public String generateWebToken(ApplicationUser applicationUser) {
		Map<String, Object> claims = new HashMap<>();
		return createWebToken(claims, applicationUser.getUsername());
	}
	
	
	/**
	 * 
	 * @param claims
	 * @param owner
	 * @return JWT 
	 * 
	 * This method is called by the generateWebToken method to create a token with the secret key and 
	 * ApplicationUser username.
	 */
	private String createWebToken(Map<String, Object> claims, String owner) {
		return Jwts.builder().setClaims(claims).setSubject(owner)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	
	
	public Boolean validateWebToken(String token, ApplicationUser applicationUser) {
		
		final String username = extractUsername(token);
		return (username.equals(applicationUser.getUsername()));
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
}

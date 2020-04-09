package com.revature.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.revature.beans.ApplicationUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtility {

	private String SECRET_KEY = "revaturesupersecurekeyfortherideshareapplicationitissupposedtobethislongyesiknowitislong";
	
	
	/**
	 * 
	 * @param applicationUser
	 * @return A new JWT 
	 * 
	 * This method takes in an ApplicationUser (Which implements the Spring Security User Class) and calls the create token method with 
	 * the user's username.
	 */
	public String generateWebToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createWebToken(claims, userDetails.getUsername());
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
		return Jwts.builder().setClaims(claims).setSubject(owner).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).compact();
	}
	
	
	
	public Boolean validateWebToken(String token, UserDetails applicationUser) {
		
		final String username = extractUsername(token);
		return (username.equals(applicationUser.getUsername()));
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		Jws<Claims> claimsJws;
		claimsJws = Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.build()
				.parseClaimsJws(token);
		Claims body = claimsJws.getBody();
		return body;
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
}

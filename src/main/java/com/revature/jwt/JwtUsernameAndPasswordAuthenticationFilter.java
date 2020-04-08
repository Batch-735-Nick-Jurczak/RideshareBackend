package com.revature.jwt;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authManager;
	
	

	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager) {
		this.authManager = authManager;
	}



	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		try {
			UsernameAndPasswordAuthenticationRequest authRequest = new ObjectMapper()
					.readValue(request.getInputStream(),UsernameAndPasswordAuthenticationRequest.class);
			
			Authentication auth = new UsernamePasswordAuthenticationToken(
					authRequest.getUsername(),
					authRequest.getPassword()
					);
			Authentication authenticate = authManager.authenticate(auth);
			return authenticate;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}



	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String key = "revaturesupersecurekeyfortherideshareapplicationitissupposedtobethislongyesiknowitislong";
		String token = Jwts.builder()
		.setSubject(authResult.getName())
		.claim("authorities", authResult.getAuthorities())
		.setIssuedAt(new Date())
		.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(7)))
		.signWith(Keys.hmacShaKeyFor(key.getBytes()))
		.compact();
		
		response.addHeader("Authorization", "Berrer " + token);
	}
	
}

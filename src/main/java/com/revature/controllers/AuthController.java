package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.ApplicationUser;
import com.revature.jwt.AuthenticationResponse;
import com.revature.jwt.JwtUtility;
import com.revature.jwt.UsernameAndPasswordAuthenticationRequest;
import com.revature.services.impl.ApplicationUserService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private ApplicationUserService appService;
	
	@Autowired
	private JwtUtility jwtUtil;
	
	
	@PostMapping
	public ResponseEntity<?> createAuthToken(@RequestBody UsernameAndPasswordAuthenticationRequest authRequest) throws Exception {
		
		System.out.println("making it to the endpoint");
		try {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			}
		catch (BadCredentialsException e) {
				throw new Exception("Invalid username and password combination", e);
			}
		final UserDetails appUser = appService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtUtil.generateWebToken(new ApplicationUser(appUser));
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}
}

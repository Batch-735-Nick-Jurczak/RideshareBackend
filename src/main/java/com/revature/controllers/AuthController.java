package com.revature.controllers;

import java.io.PrintWriter;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Admin;
import com.revature.beans.ApplicationUser;
import com.revature.beans.User;
import com.revature.jwt.AuthenticationResponse;
import com.revature.jwt.JwtUtility;
import com.revature.jwt.UsernameAndPasswordAuthenticationRequest;
import com.revature.services.UserService;
import com.revature.services.impl.AdminServiceImpl;
import com.revature.services.impl.ApplicationUserService;
import com.revature.services.impl.UserServiceImpl;

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
	
	@Autowired
	private UserServiceImpl us;
	
	@Autowired 
	private AdminServiceImpl as;
	
	
	/**
	 * Method used to create a JWT with it's paired user/admin, if the user exists in the ApplicationUser table. This endpoint
	 * needs to be called when registering any new user or admin.
	 * 
	 * @param authRequest A UsernameAndPasswordAuthenticationRequest object, just contains username and password
	 * @return Returns a response entity along with a user OR admin and their respective web token.
	 * @throws Exception Will throw a bad credentials exception if the application user is not found.
	 */
	
	@PostMapping
	public ResponseEntity<?> createAuthToken(@RequestBody UsernameAndPasswordAuthenticationRequest authRequest) throws Exception {
		
		try {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			}
		catch (BadCredentialsException e) {
				throw new Exception("Invalid username and password combination", e);
			}
		final UserDetails appUser = appService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtUtil.generateWebToken(new ApplicationUser(appUser));
		
	
		Optional<User> user = us.getUserByUsername(authRequest.getUsername());
		Optional<Admin> admin = as.getAdminById(authRequest.getUsername());
		
		if(user.isPresent()) {
			user.get().setToken(jwt);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(user.get());
			return ResponseEntity.ok(json);
		}
		else {
			
			admin.get().setToken(jwt);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(admin.get());
			return ResponseEntity.ok(json);
		}
			


	
		
	}
}

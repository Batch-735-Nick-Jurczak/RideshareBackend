package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Admin;
import com.revature.beans.ApplicationUser;
import com.revature.services.AdminService;
import com.revature.services.impl.ApplicationUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/app")
@CrossOrigin
public class ApplicationController {
	@Autowired
	private ApplicationUserService as;
	
	
	/**
	 * This method will return all the application users. 
	 * @return Returns a list of all application users
	 */
	@ApiOperation(value="Returns all applicationUsers")
	@GetMapping
	public List<ApplicationUser> getAppUsers() {
		return as.getAppUsersByUsername();
	}

}

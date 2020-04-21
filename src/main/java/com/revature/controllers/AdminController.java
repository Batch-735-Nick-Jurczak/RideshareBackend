package com.revature.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Admin;
import com.revature.beans.ApplicationUser;
import com.revature.services.AdminService;
import com.revature.services.impl.ApplicationUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * AdminController takes care of handling our requests to /admins.
 * It provides methods that can perform tasks like all admins, admin by username, add admin, update admin, and
 * delete admin by username.
 * 
 * @author Adonis Cabreja, David Anderson
 *
 */

@RestController
@RequestMapping("/admins")
@CrossOrigin
@Api(tags= {"Admin"})
public class AdminController {
	
	
	
	@Autowired
	private AdminService as;
	
	@Autowired
	private ApplicationUserService appService;
	
	/**
	 * HTTP GET method (/users)
	 * TO-DO DELETE THIS??
	 * @return A list of all the admins.
	 */
	
	@ApiOperation(value="Returns all admins", tags= {"Admin"})
	@GetMapping
	public List<Admin> getAdmins() {
		
		return as.getAdmins();
	}
	
	/**
	 * HTTP GET method (/users/{username})
	 * 
	 * @param username represents the admin's username.
	 * @return An admin that matches the username.
	 */
	
	@ApiOperation(value="Returns admin by username", tags= {"Admin"})
	@GetMapping("/{username}")
	public Admin getAdminById(@PathVariable("username") String username) {
		
		return as.getAdminById(username).get();
	}
	
	/**
	 * HTTP POST method (/admins)
	 * 
	 * Returns the admin object, as well as adds the admin to the ApplicationUser table with the role of "ADMIN"
	 * for Spring Security
	 * 
	 * @param admin represents the new Admin object being sent.
	 * @return The newly created object with a 201 code.
	 * 
	 */
	
	@ApiOperation(value="Adds a new admin", tags= {"Admin"})
	@PostMapping
	public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) {
		
		return new ResponseEntity<>(as.createAdmin(admin), HttpStatus.CREATED);
	}
	
	/**
	 * HTTP PUT method (/users)
	 * 
	 * @param admin represents the updated Admin object being sent.
	 * @return The newly updated object.
	 */
	
	@ApiOperation(value="Updates admin by username", tags= {"Admin"})
	@PutMapping("/{username}")
	public Admin updateAdmin(@Valid @RequestBody Admin admin) {
		
		return as.updateAdmin(admin);
	}
	
	/**
	 * HTTP DELETE method (/users/{username})
	 * 
	 * @param username represents the admin's username.
	 * @return A string that says which admin was deleted.
	 */
	
	@ApiOperation(value="Deletes an admin by username", tags= {"Admin"})
	@DeleteMapping("/{username}")
	public String deleteAdmin(@PathVariable("username")String username) {
		
		return as.deleteAdminById(username);
	}
}

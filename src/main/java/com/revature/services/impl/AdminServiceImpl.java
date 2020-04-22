package com.revature.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Admin;
import com.revature.repositories.AdminRepository;
import com.revature.services.AdminService;

/**
 * AdminServiceImpl handles any additional services that need to be made before calling the
 * repository methods.
 * 
 * @author Adonis Cabreja
 *
 */

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository ar;
	
	/**
	 * Calls AdminRepository's findAll method found in the JpaRepository.
	 * 
	 * @return A list of all the admins.
	 */
	
	@Override
	public List<Admin> getAdmins() {
		return ar.findAll();
	}


	/**
	 * Calls AdminRepository's save method found in the JpaRepository.
	 * 
	 * @param admin represents the new Admin object being sent.
	 * @return The newly created object.
	 */
	
	@Override
	public Admin createAdmin(Admin admin) {
		return ar.save(admin);
	}

	/**
	 * Calls AdminRepository's save method found in the JpaRepository.
	 * 
	 * @param admin represents the updated Admin object being sent.
	 * @return The newly updated object.
	 */
	
	@Override
	public Admin updateAdmin(Admin admin) {
		return ar.save(admin);
	}
	
	/**
	 * Calls AdminRepository's deleteById method found in the JpaRepository.
	 * 
	 * @param username represents admin's username.
	 * @return A string that says which admin was deleted.
	 */
	
	@Override
	public String deleteAdminById(String username) {
		ar.deleteById(username);
		return "Admin with username: "+ username + " was deleted.";
	}

	
	public Optional<Admin> getAdminById(String username){
		return ar.getAdminByUsername(username);
	}


}

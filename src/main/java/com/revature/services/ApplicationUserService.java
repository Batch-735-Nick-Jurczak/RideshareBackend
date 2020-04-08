package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revature.repositories.ApplicationUserRepository;

@Service
public class ApplicationUserService implements UserDetailsService {

	
	private ApplicationUserRepository aur;
	
	/**
	 * 
	 * @param ApplicationUserRepository
	 * 
	 * This constructor creates/autowires our Application User Repository into our 
	 * Application User service
	 */
	@Autowired
	public ApplicationUserService(ApplicationUserRepository aur) {
		super();
		this.aur = aur;
	}


	/**
	 * @param String username
	 * 
	 * @return An ApplicationUser (it implements UserDetails)
	 * 
	 * This is essentially a find by id for our application user.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		
		return aur.getOne(username);	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
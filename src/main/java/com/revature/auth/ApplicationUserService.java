package com.revature.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.revature.beans.User;
import com.revature.repositories.ApplicationUserRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;
import com.revature.services.impl.UserServiceImpl;

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

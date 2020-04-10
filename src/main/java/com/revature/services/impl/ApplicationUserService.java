package com.revature.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.beans.ApplicationUser;
import com.revature.beans.User;
import com.revature.repositories.ApplicationUserRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.ApplicationUserServe;

@Service
public class ApplicationUserService implements ApplicationUserServe,UserDetailsService {

	
	private ApplicationUserRepository aur;
	private AdminServiceImpl as;
	private UserServiceImpl us;
	/**
	 * This constructor autowires the services needed for the application user service
	 *  
	 * @param aur  Autowired ApplicationUser Repository
	 * @param as Autowired Admin Service
	 * @param us Autowired User service
	 */
	@Autowired
	public ApplicationUserService(ApplicationUserRepository aur, AdminServiceImpl as, UserServiceImpl us) {
		super();
		this.aur = aur;
		this.as = as;
		this.us = us;
	}

	/**
	 * This is essentially a find by id for our application user.
	 * 
	 * @param username Application user's username
	 * 
	 * @return An ApplicationUser (it implements UserDetails)
	 */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	
		Optional<ApplicationUser> user = aur.getUserByUsername(username);
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		final org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder().passwordEncoder(encoder::encode);
		
		UserDetails users = userBuilder
				.username(user.get().getUsername())
				.password(user.get().getPassword())
				.roles(user.get().getRole())
				.build();
		return users;
	}
	


	/**
	 * This method returns a list of all the application users
	 * 
	 * @return A list of all the applicationUsers in the H2 database
	 */
	public List<ApplicationUser> getAppUsersByUsername(){
			return aur.findAll();
		}
	
	
	public void insertApplicationUser(ApplicationUser appUser) {
		aur.save(appUser);
	}
	
	
}
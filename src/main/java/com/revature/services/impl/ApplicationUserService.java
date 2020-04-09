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
	public List<ApplicationUser> getAppUsersByUsername(){
			return aur.findAll();
		}
	
	
	
}
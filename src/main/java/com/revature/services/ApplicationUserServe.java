package com.revature.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.revature.beans.ApplicationUser;

public interface ApplicationUserServe {
	public List<ApplicationUser> getAppUsersByUsername();
}

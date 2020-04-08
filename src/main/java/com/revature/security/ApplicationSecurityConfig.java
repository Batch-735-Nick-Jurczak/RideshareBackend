package com.revature.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.revature.auth.ApplicationUserService;
import com.revature.jwt.JwtTokenVerifier;
import com.revature.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService appService;
	
	
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService appService) {
		this.passwordEncoder = passwordEncoder;
		this.appService = appService;
	}
	

	/**
	 * This method uses our custom Application User Service to get the principle. For JPA you have to use a custom service, 
	 * as spring security doesn't have an out of the box solution. 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(appService);
		
	}
	
	/**
	 * This is where our roles get the endpoints they are authorized to view set. 
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/**").hasAnyRole("USER", "ADMIN");
			
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
	
}
  
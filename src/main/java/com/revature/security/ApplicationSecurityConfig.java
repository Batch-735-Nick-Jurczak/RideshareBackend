package com.revature.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.revature.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.revature.services.ApplicationUserService;


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
		http
			//.sessionManagement()
			//.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			//.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager))
			.authorizeRequests()
			.antMatchers("/**").permitAll();
		//	.antMatchers("/**").hasAnyRole("USER", "ADMIN");
			
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
	
}
  
package com.revature.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	
	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests().antMatchers("/login").permitAll() //this is route based permissions
		.antMatchers("/admins").hasRole(ApplicationUserRole.ADMIN.name()) //this is a role based authentication
		.antMatchers(HttpMethod.GET,"/admins").hasAuthority(ApplicationUserPermissions.ADMIN_READ.name())
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		UserDetails anneSmith =  User.builder()
				.username("Anne Smith")
				.password(passwordEncoder.encode("password"))
				.roles(ApplicationUserRole.ADMIN.name())
				.build();
		
				UserDetails tomUser =  User.builder()
				.username("tom")
				.password(passwordEncoder.encode("password"))
				.roles(ApplicationUserRole.ADMINTRAINEE.name())
				.build();
		
		return new InMemoryUserDetailsManager(anneSmith, tomUser);
		

		
	}

	
	
}
  
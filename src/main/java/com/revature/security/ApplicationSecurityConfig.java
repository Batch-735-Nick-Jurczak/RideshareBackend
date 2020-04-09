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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.revature.jwt.JwtTokenVerifier;
import com.revature.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.revature.services.impl.ApplicationUserService;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private ApplicationUserService appService;
	
	private JwtTokenVerifier jwtTokenVerifier;
	
	
	@Autowired
	public ApplicationSecurityConfig(ApplicationUserService appService, JwtTokenVerifier jwtTokenVerifier) {
		
		this.appService = appService;
		this.jwtTokenVerifier = jwtTokenVerifier;
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
		http.csrf().disable()
			.authorizeRequests().antMatchers("/auth").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtTokenVerifier,  UsernamePasswordAuthenticationFilter.class);
			//.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager))
//			.authorizeRequests()
//			.antMatchers("/admins").hasRole("USER")
//			.antMatchers("/","/app").permitAll().and().formLogin();
		//	.antMatchers("/**").hasAnyRole("USER", "ADMIN");
			
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
}
  
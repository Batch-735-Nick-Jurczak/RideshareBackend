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


/**
 * This class is the configuration class for Spring Security
 * 
 * @author David Anderson
 *
 */


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private ApplicationUserService appService;
	
	private JwtTokenVerifier jwtTokenVerifier;
	
	/**
	 * Creates a ApplicationSecurityConfig object with a ApplicationUserService and JwtTokenVerifier
	 * 
	 * @param appService
	 * @param jwtTokenVerifier
	 */
	
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
	
	
	/**
	 * A required bean by Spring Security. The Authentication Manager manages your authentication providers, which are where the work is done.
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	/**
	 * Our password encoder for encoding passwords into our ApplicationUser repository. Right now we are not encrypting passwords at all,
	 * but Spring Security expects this method here. The NoOpPassword is shown as deprecated because it's bad practice to persist actual passwords.
	 * 
	 * @return Returns the PasswordEncoder object
	 */
	
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
}
  
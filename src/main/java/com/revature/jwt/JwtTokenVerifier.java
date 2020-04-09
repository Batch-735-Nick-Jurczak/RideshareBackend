package com.revature.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;
import com.revature.services.impl.ApplicationUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@Component
public class JwtTokenVerifier extends OncePerRequestFilter {
	
	
	@Autowired
	private ApplicationUserService appUserService;
	
	@Autowired
	private JwtUtility jwtUtil;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		System.out.println("Inside the Filter");
		
		
		String authHeader = req.getHeader("Authorization");
		System.out.println(authHeader);
		
		
		if(Strings.isNullOrEmpty(authHeader)|| !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(req, resp);
			System.out.println("did not work");
			return;
		}
		
		Jws<Claims> claimsJws;
		String token = authHeader.substring(7);
		System.out.println(token + "line 56");
		String usernameFromToken = jwtUtil.extractUsername(token);
		System.out.println(usernameFromToken);
		try {
			
			
			String secretKey = "revaturesupersecurekeyfortherideshareapplicationitissupposedtobethislongyesiknowitislong";
			
			claimsJws = Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.build()
				.parseClaimsJws(token);
			
			
			
			
		if(usernameFromToken != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.appUserService.loadUserByUsername(usernameFromToken);
			if (jwtUtil.validateWebToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		}catch(JwtException ex){
			throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
		}
		filterChain.doFilter(req, resp);
	}

}

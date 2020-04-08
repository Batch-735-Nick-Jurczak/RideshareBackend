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
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenVerifier extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = req.getHeader("Authoriztion");
		
		if(Strings.isNullOrEmpty(authHeader)|| !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(req, resp);
			return;
		}
		
		Jws<Claims> claimsJws;
		String token = authHeader.replace("Bearer ", "");
		try {
			
			
			String secretKey = "revaturesupersecurekeyfortherideshareapplicationitissupposedtobethislongyesiknowitislong";
			
			claimsJws = Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.build()
				.parseClaimsJws(token);
			
			Claims body = claimsJws.getBody();
			
			String username = body.getSubject();
			
			@SuppressWarnings("unchecked")
			List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("auhorities");
			
			Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
					.map(m-> new SimpleGrantedAuthority(m.get("authority")))
					.collect(Collectors.toSet());
			
			Authentication auth = new UsernamePasswordAuthenticationToken(
					username,
					null,
					simpleGrantedAuthorities
					);
			
			SecurityContextHolder.getContext().setAuthentication(auth);
		}catch(JwtException ex){
			throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
		}
		filterChain.doFilter(req, resp);
	}

}

package com.example.empms.util;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	private static final String SECRET_KEY = "mysecrectkeyemployeemanagementssecretkey123"; // Replace with your actual secret key
	
	public String generateToken(String username) {
		// In a real application, you would use a library like jjwt to generate a JWT token
		// Here, we will just return a dummy token for demonstration purposes
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.compact();
	}
	
	public String extractUsername(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	

}

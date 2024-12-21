package com.infy.Login.Utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
	private String SECRET_KEY = "BABA_YAGA"; // Change this to a strong key
	private long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

	public String generateToken(String email) {
		Map<String, Object> claims = new HashMap<>();
		String userName = email.split("@")[0];
		claims.put("email", email);
		claims.put("username", userName);
		return createToken(claims, email);
	}

	private String createToken(Map<String, Object> claims, String email) {
		return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public boolean validateToken(String token, String identity) {
		String username = identity.split("@")[0];
		String extractedIdentity = extractUsername(token);
		String extractedUserName = extractedIdentity.split("@")[0];
		return (extractedIdentity.equals(identity)
				|| username.equalsIgnoreCase(extractedUserName) && !isTokenExpired(token));
	}

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		return extractAllClaims(token).getExpiration().before(new Date());
	}
}
package com.dineshlearnings.springsecurityjwt.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	
	@Value("${app.secret}")
	private String secret;
	
	// 6. Validate the Token with  User name
	public Boolean validateToken(String token, String userName) {
		String tokenUserName = getUserName(token);
		return (tokenUserName.equals(userName) && !isTokenExpired(token));
	}
	
	
	// 5. Validating the Token Expire date
	public Boolean isTokenExpired(String token) {
		Date tokenExpDate = getExpiredDate(token);
		return tokenExpDate.before(new Date(System.currentTimeMillis()));
	}
	
	// 4. Get User name
	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}
	
	
	// 3. Get Expired Date
	public Date getExpiredDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	
	// 2. Read Claims
	public Claims getClaims(String token) {
		
		return Jwts.parser()
				.setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody();
	}
	
	// 1. Generate Token
	public String generateToken(String subject) {
		
		return Jwts.builder()
				.setSubject(subject)
				.setIssuer("Dinesh")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
}

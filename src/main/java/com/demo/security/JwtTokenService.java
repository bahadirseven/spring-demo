package com.demo.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService {

	private final String SECRET_KEY = "fobbSecretKey";

	public String generateToken(String username) {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + (86400000));

		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(now)
				.setExpiration(expiredDate)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}

	public boolean tokenValidate(String token) {
		return token != null && getClaims(token).getExpiration().after(new Date());
	}

	public Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}
}

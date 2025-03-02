package com.auth.proyectofinal.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {
	@Value("${security.jwt.key}")
	private String jwtKey;
	@Value("${security.user.generator}")
	private String userGenerator;
	
	public String createToken(Authentication auth) {
		Algorithm algorithm = Algorithm.HMAC256(jwtKey);
		
		String username = auth.getPrincipal().toString();
		
		String authorities = auth.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		String jwt = JWT.create()
				.withIssuer(userGenerator)
				.withSubject(username)
				.withClaim("authorities", authorities)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis()+(30*60000)))
				.withJWTId(UUID.randomUUID().toString())
				.withNotBefore(new Date(System.currentTimeMillis()))
				.sign(algorithm);
		return jwt;		
	}
	
	public DecodedJWT validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(jwtKey);
			JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(userGenerator).build();
			return jwtVerifier.verify(token);
		}catch(JWTVerificationException e) {
			throw new JWTVerificationException("Invalid token, not authorized");
		}
	}
	
	public String getUsername(DecodedJWT decodedJWT) {
		return decodedJWT.getSubject().toString();
	}
	
	public Claim getSpecificClaim(DecodedJWT decodedJwt, String claimName) {
		return decodedJwt.getClaim(claimName);
	}
	
	public Map<String,Claim> getClaims(DecodedJWT decodeJwt){
		return decodeJwt.getClaims();
	}
}

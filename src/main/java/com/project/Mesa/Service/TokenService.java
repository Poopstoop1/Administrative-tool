package com.project.Mesa.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {

	@Value("${api.security.token.secret}")
	private String SECRET;

	public String generateToken(String username) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(SECRET);

			return JWT.create().withIssuer("mesa-api").withSubject(username).withExpiresAt(generateExpirationDate())
					.sign(algorithm);

		} catch (JWTCreationException e) {
			throw new RuntimeException("Erro ao gerar token", e);
		}
	}

	public String validateToken(String token) {
    	try {
    		 Algorithm algorithm = Algorithm.HMAC256(SECRET);
    		 
    		 return JWT.require(algorithm).withIssuer("mesa-api").build().verify(token).getSubject();
		} catch (JWTVerificationException e) {
			return "";
		}
    	
    }

	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

}

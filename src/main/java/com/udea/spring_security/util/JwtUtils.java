package com.udea.spring_security.util;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {


    @Value("${jwt.private-key}")
    private String privateKey;

    @Value("${jwt.user-generator}")
    private String userGenerator;

    public String createToken(Authentication authentication, String document) {

        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String username = authentication.getPrincipal().toString();
        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withClaim("document", document)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date())
                .sign(algorithm);

        return jwtToken;
    }

    public DecodedJWT validateToken(String token) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier verifier = JWT.require(algorithm).withIssuer(this.userGenerator).build();

            DecodedJWT decodedJwt = verifier.verify(token);

            return decodedJwt;

        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid token, not authorized");
        }
    }

    public String extractUsername(DecodedJWT decodedJwt) {
        return decodedJwt.getSubject().toString();
    }

    public Claim getSpecificClaim(DecodedJWT decodedJwt, String claim) {
        return decodedJwt.getClaim(claim);
    }

    public Map<String, Claim> getAllClaims(DecodedJWT decodedJwt) {
        return decodedJwt.getClaims();
    }
}
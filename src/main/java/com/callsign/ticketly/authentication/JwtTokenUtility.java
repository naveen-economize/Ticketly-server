package com.callsign.ticketly.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenUtility implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000; //hours * minutes * seconds * milliseconds

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public String getUserEmailFromToken(String token) {
        return getClaimFromJWTToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromJWTToken(token, Claims::getExpiration);
    }

    public  <T> T getClaimFromJWTToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String userEmail = getUserEmailFromToken(token);
        return userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String generateToken(String userEmail){
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
}
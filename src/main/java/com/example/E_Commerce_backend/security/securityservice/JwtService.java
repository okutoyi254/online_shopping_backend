package com.example.E_Commerce_backend.security.securityservice;

import com.example.E_Commerce_backend.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationMs;

    public JwtService(JwtProperties prop) {

        byte[] keyBytes= Decoders.BASE64.decode(prop.getSecret());
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = prop.getExpiration();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){ return extractClaim(token,Claims::getExpiration);}



    public String generateToken(String phoneNumber){
        Map<String,Object>claims=new HashMap<>();
        return createToken(claims,phoneNumber);
    }

    public String createToken(Map<String,Object>claims,String phoneNumber){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }
    private Claims extractClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T>claimsResolver){

        final Claims claims=extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token,UserDetails userDetails){
            final String username=extractUsername(token);
                return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}

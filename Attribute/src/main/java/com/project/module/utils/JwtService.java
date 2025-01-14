package com.project.module.utils;

import com.project.module.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Value("3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b")
    private String secretKey;

//    .setExpiration(new Date(System.currentTimeMillis() + 1000*60*TOTAL_MINUTE))
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    int MINUTE_EXPIRATION = 5; // 1 Minutes
//    int MINUTE_EXPIRATION = 60*2; // 120 Minutes (2 hours)
//    int MINUTE_EXPIRATION = 60*4; // 240 Minutes (4 hours)

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims,UserDetails userDetails){
        return buildToken(extraClaims, userDetails.getUsername());
    }

    public long getExpirationTime(){
        return EXPIRATION_TIME;
    }

    private String buildToken(Map<String, Object> claims, String subject){
        long expirationTime = System.currentTimeMillis() + (1000L * 60 * MINUTE_EXPIRATION);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}

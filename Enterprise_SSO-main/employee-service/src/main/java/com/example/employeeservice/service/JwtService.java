package com.example.employeeservice.service;

import com.example.employeeservice.model.Token;
import com.example.employeeservice.model.User;
import com.example.employeeservice.repository.TokenRepository;
import com.example.employeeservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.employeeservice.config.JwtConfigProperties;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtService {

    private final JwtConfigProperties jwtConfigProperties;
    private java.security.Key signingKey;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public JwtService(JwtConfigProperties jwtConfigProperties) {
        this.jwtConfigProperties = jwtConfigProperties;
    }

    @PostConstruct
    public void init() {
        signingKey = Keys.hmacShaKeyFor(jwtConfigProperties.getSecret().getBytes());
    }

    public String generateToken(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Optional<Token> existingTokenOpt = tokenRepository.findByUser(user);
        if (existingTokenOpt.isPresent()) {
            Token existingToken = existingTokenOpt.get();
            if (!isTokenExpired(existingToken.getToken()) && (!existingToken.isLoggedOutEmployee() || !existingToken.isLoggedOutSalary())) {
                // Overwrite the token value if any of the services is not logged out
                String newTokenString = createToken(new HashMap<>(), username);
                existingToken.setToken(newTokenString);
                existingToken.setLoggedOutEmployee(false);
                existingToken.setLoggedOutSalary(false);
                tokenRepository.save(existingToken);
                return newTokenString;
            } else if (existingToken.isLoggedOutEmployee() && existingToken.isLoggedOutSalary()) {
                // If the user has logged out from both services, generate a new token and update the entry
                String newTokenString = createToken(new HashMap<>(), username);
                existingToken.setToken(newTokenString);
                existingToken.setLoggedOutEmployee(false);
                existingToken.setLoggedOutSalary(false);
                tokenRepository.save(existingToken);
                return newTokenString;
            }
        }

        // If no existing token, create a new one
        String tokenString = createToken(new HashMap<>(), username);
        Token newToken = new Token(tokenString);
        newToken.setUser(user);
        tokenRepository.save(newToken);

        return tokenString;
    }


    private void invalidateExistingToken(User user) {
        Optional<Token> existingTokenOpt = tokenRepository.findByUser(user);
        existingTokenOpt.ifPresent(tokenRepository::delete);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && isTokenValid(token));
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void logoutToken(String token, String service) {
        Token existingToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token not found"));
        if ("employee".equals(service)) {
            existingToken.setLoggedOutEmployee(true); // Mark the token as logged out for employee service
        } else if ("salary".equals(service)) {
            existingToken.setLoggedOutSalary(true); // Mark the token as logged out for salary service
        }
        tokenRepository.save(existingToken);
    }


    public boolean isTokenValid(String token) {
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        return tokenOptional.isPresent() && !isTokenExpired(token);
    }

    public boolean isTokenValidForService(String token, String service) {
        Optional<Token> tokenOptional = tokenRepository.findByToken(token);
        if (tokenOptional.isPresent() && !isTokenExpired(token)) {
            Token tokenEntity = tokenOptional.get();
            if ("employee".equals(service)) {
                return !tokenEntity.isLoggedOutEmployee();
            } else if ("salary".equals(service)) {
                return !tokenEntity.isLoggedOutSalary();
            }
        }
        return false;
    }
}
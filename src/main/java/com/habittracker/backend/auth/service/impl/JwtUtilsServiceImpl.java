package com.habittracker.backend.auth.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import com.habittracker.backend.auth.service.JwtUtilsService;
import com.habittracker.backend.user.persistence.User;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtilsServiceImpl implements JwtUtilsService {

  @Value("${jwt.secret.base64}")
  private String SECRET_KEY;

  @Value("${jwt.access-token.expiration-time.ms}")
  private Integer ACCESS_TOKEN_EXPIRATION_TIME;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(
      User user
  ) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", mapRoles(user));
    claims.put("username", user.getUsername());
    claims.put("id", user.getId());
    claims.put("firstName", user.getFirstName());
    claims.put("lastName", user.getLastName());
    claims.put("email", user.getEmail());
    return generateToken(claims, user);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      User user
  ) {
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token) {
    try {
      Jwts
          .parserBuilder()
          .setSigningKey(getSigningKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
      return !isTokenExpired(token);
    } catch (Exception e) {
      return false;
    }
  }

  private List<String> mapRoles(User user) {
    return user.getAuthorities()
            .stream().
            map(GrantedAuthority::getAuthority)
            .toList();
  }

  private boolean isTokenExpired(String token) {
    return extractExpirationDate(token).before(new Date());
  }

  private Date extractExpirationDate(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}

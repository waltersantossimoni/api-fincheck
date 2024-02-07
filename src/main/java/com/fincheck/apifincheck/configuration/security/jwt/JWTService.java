package com.fincheck.apifincheck.configuration.security.jwt;

import com.fincheck.apifincheck.model.AuthenticatedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTService {
  private final Clock clock;

  @Value("${fincheck.jwt.secret-key}")
  private String SECRET_KEY;

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
  }

  private MacAlgorithm getAlgorithm() {
    return SIG.HS256;
  }

  public boolean isTokenValid(String token, AuthenticatedUser userDetails) {
    final String username = extractUsername(token);

    return isUsernameValid(userDetails, username) && !isTokenExpired(token);
  }

  public String extractUsername(String jwt) {
    return extractClaim(jwt, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    Claims claims = extractAllClaims(token);

    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
      .parser()
      .verifyWith(getSigningKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

  private boolean isUsernameValid(AuthenticatedUser userDetails, String username) {
    return username.equals(userDetails.getId().toString());
  }

  public boolean isTokenExpired(String token) {
    return extractExpiration(token) < clock.millis();
  }

  public Long extractExpiration(String jwt) {
    Date date = extractClaim(jwt, Claims::getExpiration);

    return date.getTime();
  }

  public String generateToken(AuthenticatedUser userDetails) {
    return generateToken(new HashMap<>(), userDetails, 24L);
  }

  public String generateToken(
    Map<String, Object> extraClaims,
    AuthenticatedUser userDetails,
    Long hours
  ) {
    Long oneHour = 3600000L;

    return Jwts
        .builder()
        .signWith(getSigningKey(), getAlgorithm())
        .subject(userDetails.getId().toString())
        .issuedAt(new Date(clock.millis()))
        .expiration(new Date(clock.millis() + (oneHour * hours)))
        .claims(extraClaims)
        .compact();
  }
}

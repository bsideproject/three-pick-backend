package com.bside.threepick.domain.security.service;

import static org.springframework.util.StringUtils.hasText;

import com.bside.threepick.domain.security.dto.response.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

  private final StringRedisTemplate stringRedisTemplate;
  @Value("${security.secret.key}")
  private String secretKey;
  @Value("${front.server.host}")
  private String frontServerHost;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  private long makePeriod(long tokenMinute) {
    return 1000L * 60L * tokenMinute;
  }

  public Token generateToken(String subject, Long accountId, String role) {
    long accessTokenMinutes = 10L;
    long refreshTokenMinutes = accessTokenMinutes * 6L;

    Claims claims = Jwts.claims().setSubject(subject);
    claims.put("role", role);
    claims.put("accountId", accountId);

    Date now = new Date();
    String accessToken = Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + makePeriod(accessTokenMinutes)))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();

    stringRedisTemplate.opsForValue()
        .set(String.valueOf(accountId), accessToken);
    stringRedisTemplate.expire(String.valueOf(accountId), accessTokenMinutes, TimeUnit.MINUTES);

    return new Token(accountId, accessToken
        , Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + makePeriod(refreshTokenMinutes)))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact());
  }

  public boolean verifyToken(String token, boolean accessToken) {
    if (accessToken) {
      String accountId = String.valueOf(getAccountId(token));
      String accessTokenFromRedis = stringRedisTemplate.opsForValue()
          .get(accountId);

      if (!token.equals(accessTokenFromRedis)) {
        return false;
      }
    }

    try {
      Jws<Claims> claims = Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token);
      return claims.getBody()
          .getExpiration()
          .after(new Date());
    } catch (Exception e) {
      return false;
    }
  }

  public String getSubject(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String getRole(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role", String.class);
  }

  public Long getAccountId(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("accountId", Long.class);
  }

  public String getAccessToken(HttpServletRequest request) {
    String token = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (hasText(token)) {
      token = token.replace("Bearer ", "");
    }
    return token;
  }

  public void responseToken(HttpServletResponse response, Token token) {
    response.setStatus(HttpStatus.SEE_OTHER.value());
    response.setHeader(HttpHeaders.LOCATION, makeLocation(token));
  }

  public String makeLocation(Token token) {
    return frontServerHost + "?auth=" + token.getAccessToken()
        + "&refresh=" + token.getRefreshToken() + "&account-id=" + token.getAccountId();
  }

  public void logout(HttpServletRequest request) {
    String accessToken = getAccessToken(request);
    if (accessToken == null) {
      return;
    }
    String accountId = String.valueOf(getAccountId(accessToken));
    String accessTokenFromRedis = stringRedisTemplate.opsForValue().get(accountId);
    if (accessToken.equals(accessTokenFromRedis)) {
      stringRedisTemplate.delete(accountId);
    }
  }
}

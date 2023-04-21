package com.bside.threepick.domain.security.service;

import com.bside.threepick.domain.security.dto.response.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

  @Value("${security.secret.key}")
  private String secretKey;

  @Value("${front.server.host}")
  private String frontServerHost;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public Token generateToken(String subject, Long accountId, String role) {
    long tokenPeriod = 1000L * 60L * 10L;
    long refreshPeriod = 1000L * 60L * 60L;

    Claims claims = Jwts.claims().setSubject(subject);
    claims.put("role", role);
    claims.put("accountId", accountId);

    Date now = new Date();
    return new Token(accountId,
        Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenPeriod))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact(),
        Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshPeriod))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact());
  }

  public boolean verifyToken(String token) {
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

  public void responseToken(HttpServletResponse response, Token token) throws IOException {
    response.sendRedirect(frontServerHost + "?auth=" + token.getAccessToken()
        + "&refresh=" + token.getRefreshToken() + "account-id=" + token.getAccountId());
  }
}

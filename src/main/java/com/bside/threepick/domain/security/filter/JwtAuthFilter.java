package com.bside.threepick.domain.security.filter;

import com.bside.threepick.domain.security.service.TokenService;
import com.bside.threepick.domain.security.user.ThreePickUserDetails;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends GenericFilterBean {

  private final TokenService tokenService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String token = ((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION);

    if (token != null && tokenService.verifyToken(token)) {
      UserDetails userDetails = ThreePickUserDetails.builder()
          .accountId(tokenService.getAccountId(token))
          .username(tokenService.getSubject(token))
          .password("")
          .authorities(tokenService.getRole(token))
          .build();
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, "",
          Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
      SecurityContextHolder.getContext()
          .setAuthentication(authenticationToken);
    }
    chain.doFilter(request, response);
  }
}

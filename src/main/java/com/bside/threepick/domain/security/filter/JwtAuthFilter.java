package com.bside.threepick.domain.security.filter;

import static org.springframework.util.StringUtils.hasText;

import com.bside.threepick.config.SecurityConfig;
import com.bside.threepick.domain.security.service.TokenService;
import com.bside.threepick.domain.security.user.ThreePickUserDetails;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends GenericFilterBean {

  private final TokenService tokenService;
  private List<AntPathRequestMatcher> antMatchers = antMatchers(SecurityConfig.getWHITELIST());

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    for (AntPathRequestMatcher antMatcher : antMatchers) {
      if (antMatcher.matches((HttpServletRequest) request)) {
        chain.doFilter(request, response);
        return;
      }
    }

    String token = tokenService.getAccessToken((HttpServletRequest) request);

    if (hasText(token) && tokenService.verifyToken(token, true)) {
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

  private List<AntPathRequestMatcher> antMatchers(String... antPatterns) {
    List<AntPathRequestMatcher> matchers = new ArrayList<>();
    for (String pattern : antPatterns) {
      matchers.add(new AntPathRequestMatcher(pattern));
    }
    return matchers;
  }
}

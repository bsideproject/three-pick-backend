package com.bside.threepick.config;

import com.bside.threepick.domain.account.reposiroty.AccountRepository;
import com.bside.threepick.domain.security.filter.JwtAuthFilter;
import com.bside.threepick.domain.security.filter.JwtExceptionFilter;
import com.bside.threepick.domain.security.filter.RequestBodyCachingFilter;
import com.bside.threepick.domain.security.service.CustomOidcAccountService;
import com.bside.threepick.domain.security.service.TokenService;
import java.util.Arrays;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

  private static String[] WHITELIST = {"/api/accounts/temp-password", "/api/accounts/{email}/auth",
      "/api/accounts/{email}/auth-check", "/api/accounts", "/api/tokens/**"};

  private final AccountRepository accountRepository;
  private final AuthenticationSuccessHandler customOAuth2SuccessHandler;
  private final AuthenticationFailureHandler customOAuth2FailureHandler;
  private final TokenService tokenService;
  private final Environment env;

  private String[] LOCAL_WHITELIST = {"/swagger-ui.html", "/swagger/**", "/swagger-resources/**", "/swagger-ui/**",
      "/swagger-resources", "/v2/api-docs", "/webjars/**", "/h2-console/**"};

  public static boolean isWhiteList(HttpServletRequest request) {
    return Arrays.asList(WHITELIST).contains(request.getRequestURI());
  }

  public static String[] getWHITELIST() {
    return WHITELIST;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public OAuth2UserService customOidcAccountService(AccountRepository accountRepository) {
    return new CustomOidcAccountService(accountRepository, passwordEncoder());
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    if (isLocalMode()) {
      WHITELIST = Stream.concat(Arrays.stream(WHITELIST), Arrays.stream(LOCAL_WHITELIST))
          .toArray(String[]::new);
      return setLocalMode(http);
    }
    return setRealMode(http);
  }

  private SecurityFilterChain setRealMode(HttpSecurity http) throws Exception {
    return http.build();
  }

  private SecurityFilterChain setLocalMode(HttpSecurity http) throws Exception {
    http.authorizeRequests(
        author -> author.antMatchers(WHITELIST)
            .permitAll()
            .anyRequest()
            .authenticated());
    http.cors().configurationSource(corsConfigurationSource());
    http.csrf().disable();
    http.headers().frameOptions().disable();
    http.oauth2Login(oauth2 -> oauth2.loginPage("/oauth2/authorization/kakao")
        .successHandler(customOAuth2SuccessHandler)
        .failureHandler(customOAuth2FailureHandler)
        .userInfoEndpoint().oidcUserService(customOidcAccountService(accountRepository)));
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(new JwtAuthFilter(tokenService), OAuth2AuthorizationRequestRedirectFilter.class);
    http.addFilterBefore(new RequestBodyCachingFilter(), JwtAuthFilter.class);
    http.addFilterBefore(new JwtExceptionFilter(), JwtAuthFilter.class);
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOriginPattern("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addExposedHeader("*");
    corsConfiguration
        .setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE"));
    corsConfiguration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return source;
  }

  private boolean isLocalMode() {
    String profile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "local";
    return profile.equals("local");
  }
}

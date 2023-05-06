package com.bside.threepick.domain.security.interceptor;

import com.bside.threepick.common.ErrorCode;
import com.bside.threepick.config.SecurityConfig;
import com.bside.threepick.domain.security.user.ThreePickUserDetails;
import com.bside.threepick.exception.UnauthorizedException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

public class AuthorizationInterceptor implements HandlerInterceptor {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof UsernamePasswordAuthenticationToken && !SecurityConfig.isWhiteList(request)) {
      ThreePickUserDetails userDetails = (ThreePickUserDetails) authentication.getPrincipal();
      Long accountId = userDetails.getAccountId();
      String email = userDetails.getUsername();

      Map<String, String> requestMap = makeRequestMap(request);
      String accountIdParam = requestMap.get("accountId");
      String emailParam = requestMap.get("email");

      if (accountIdParam != null && accountId == Long.parseLong(accountIdParam)) {
        return true;
      }

      if (email.equals(emailParam)) {
        return true;
      }
      throw new UnauthorizedException(ErrorCode.UNAUTHORIZED, "조회할 수 있는 권한이 없어요.");
    }

    return true;
  }

  private Map<String, String> makeRequestMap(HttpServletRequest request) throws java.io.IOException {
    Map<String, String> requestMap =
        (LinkedHashMap<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    if (requestMap.size() == 0) {
      ServletInputStream requestInputStream = request.getInputStream();
      requestMap = objectMapper.readValue(requestInputStream, new TypeReference<>() {
      });
    }
    return requestMap;
  }
}

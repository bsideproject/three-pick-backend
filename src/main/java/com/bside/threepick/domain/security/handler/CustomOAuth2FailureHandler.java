package com.bside.threepick.domain.security.handler;

import com.bside.threepick.common.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuth2FailureHandler implements AuthenticationFailureHandler {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());

    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("message", "삭제 된 계정이에요.");
    responseMap.put("code", ErrorCode.ACCOUNT_NOT_FOUND.getCode());
    responseMap.put("statusCode", HttpStatus.BAD_REQUEST.value());
    objectMapper.writeValue(response.getWriter(), responseMap);
  }
}

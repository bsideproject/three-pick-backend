package com.bside.threepick.domain.security.filter;

import com.bside.threepick.common.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.GenericFilterBean;

public class JwtExceptionFilter extends GenericFilterBean {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      chain.doFilter(request, response);
    } catch (JwtException ex) {
      HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
      httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
      httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());

      Map<String, Object> responseMap = new HashMap<>();
      responseMap.put("message", ErrorCode.UNAUTHORIZED.getMessage());
      responseMap.put("code", ErrorCode.UNAUTHORIZED.getCode());
      responseMap.put("statusCode", HttpStatus.BAD_REQUEST.value());
      objectMapper.writeValue(httpServletResponse.getWriter(), responseMap);
    }
  }
}

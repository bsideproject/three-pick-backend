package com.bside.threepick.domain.security.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class RequestBodyCachingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);

    filterChain.doFilter(cachedBodyHttpServletRequest, response);
  }

  private class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] cachedBody;

    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
      super(request);
      InputStream requestInputStream = request.getInputStream();
      this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
      return new CachedBodyServletInputStream(this.cachedBody);
    }

    @Override
    public BufferedReader getReader() throws IOException {
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
      return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }
  }

  private class CachedBodyServletInputStream extends ServletInputStream {

    private InputStream cachedBodyInputStream;

    public CachedBodyServletInputStream(byte[] cachedBody) {
      this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public int read() throws IOException {
      return cachedBodyInputStream.read();
    }

    @SneakyThrows
    @Override
    public boolean isFinished() {
      return cachedBodyInputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
    }
  }
}

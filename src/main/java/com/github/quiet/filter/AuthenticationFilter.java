/*
 * Copyright (C) 2023 lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.quiet.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.quiet.constant.service.Url;
import com.github.quiet.handler.ResultAuthenticationFailureHandler;
import com.github.quiet.handler.ResultAuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

  public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

  private final ObjectMapper objectMapper;

  public AuthenticationFilter(
      ResultAuthenticationSuccessHandler authenticationSuccessHandler,
      ResultAuthenticationFailureHandler authenticationFailureHandler,
      ObjectMapper objectMapper) {
    // 自定义该方式处理登录信息的登录地址，默认是 /login POST
    super(new AntPathRequestMatcher(Url.LOGIN_BY_ACCOUNT, "POST"));
    setAuthenticationSuccessHandler(authenticationSuccessHandler);
    setAuthenticationFailureHandler(authenticationFailureHandler);
    this.objectMapper = objectMapper;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    if (null != request.getContentType()
        && request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken("", "");
      UserLoginParam user;
      try (final InputStream inputStream = request.getInputStream()) {
        user = objectMapper.readValue(inputStream, UserLoginParam.class);
        authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
      } catch (final IOException e) {
        logger.error(e);
        throw new AuthenticationServiceException("Failed to read data from request", e.getCause());
      } finally {
        setDetails(request, authToken);
      }
      // 进行登录信息的验证
      return this.getAuthenticationManager().authenticate(authToken);
    } else {
      if (!request.getMethod().equals("POST")) {
        throw new AuthenticationServiceException(
            "Authentication method not supported: " + request.getMethod());
      }
      String username = obtainUsername(request);
      username = (username != null) ? username.trim() : "";
      String password = obtainPassword(request);
      password = (password != null) ? password : "";
      UsernamePasswordAuthenticationToken authRequest =
          UsernamePasswordAuthenticationToken.unauthenticated(username, password);
      // Allow subclasses to set the "details" property
      setDetails(request, authRequest);
      return this.getAuthenticationManager().authenticate(authRequest);
    }
  }

  @Nullable
  protected String obtainPassword(HttpServletRequest request) {
    return request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
  }

  @Nullable
  protected String obtainUsername(HttpServletRequest request) {
    return request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
  }

  protected void setDetails(
      HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
    authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
  }

  @Data
  static class UserLoginParam {
    private String username;
    private String password;
  }
}

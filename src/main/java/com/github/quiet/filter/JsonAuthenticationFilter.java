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
import com.github.quiet.handler.ResultAuthenticationFailureHandler;
import com.github.quiet.handler.ResultAuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Slf4j
public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final ObjectMapper objectMapper;

  public JsonAuthenticationFilter(
      ResultAuthenticationSuccessHandler authenticationSuccessHandler,
      ResultAuthenticationFailureHandler authenticationFailureHandler,
      AuthenticationManager authenticationManager,
      ObjectMapper objectMapper) {
    // 自定义该方式处理登录信息的登录地址，默认是 /login POST
    super(authenticationManager);
    setAuthenticationSuccessHandler(authenticationSuccessHandler);
    setAuthenticationFailureHandler(authenticationFailureHandler);
    this.objectMapper = objectMapper;
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    if (null != request.getContentType()
        && request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
      UsernamePasswordAuthenticationToken authToken = null;
      try (final InputStream inputStream = request.getInputStream()) {
        UserLoginParam user = objectMapper.readValue(inputStream, UserLoginParam.class);
        authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
      } catch (final IOException exception) {
        logger.error(exception);
        authToken = new UsernamePasswordAuthenticationToken("", "");
      } finally {
        setDetails(request, authToken);
      }
      // 进行登录信息的验证
      return this.getAuthenticationManager().authenticate(authToken);
    } else {
      return super.attemptAuthentication(request, response);
    }
  }

  @Data
  static class UserLoginParam {
    private String username;
    private String password;
  }
}

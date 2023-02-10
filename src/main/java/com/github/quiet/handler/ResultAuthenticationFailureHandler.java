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

package com.github.quiet.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.quiet.constant.service.MessageSourceCode;
import com.github.quiet.result.Result;
import com.github.quiet.utils.MessageSourceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登陆/认证 失败 Handler.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Component
@AllArgsConstructor
public class ResultAuthenticationFailureHandler extends AbstractResponseJsonData
    implements AuthenticationFailureHandler {

  private final MessageSource messageSource;
  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException {
    logger.error("用户登陆失败", exception);
    Result<Object> failure = Result.failure();
    failure.setCode(MessageSourceCode.Account.LOGIN_FAILURE);
    failure.setMessage(
        MessageSourceUtil.getMessage(
            request, messageSource, MessageSourceCode.Account.LOGIN_FAILURE));
    responseJsonData(response, failure);
  }
}

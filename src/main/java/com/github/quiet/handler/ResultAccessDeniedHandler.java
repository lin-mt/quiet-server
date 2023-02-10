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
import com.github.quiet.utils.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 无权访问 Handler.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Component
@AllArgsConstructor
public class ResultAccessDeniedHandler extends AbstractResponseJsonData
    implements AccessDeniedHandler {

  private final MessageSource messageSource;
  private final ObjectMapper objectMapper;

  @Override
  public void handle(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
      throws IOException {
    logger.error("用户：{} 无权限访问：{}", UserUtil.getId(), request.getRequestURI(), exception);
    response.setStatus(403);
    Result<Object> failure = Result.failure();
    failure.setCode(MessageSourceCode.Account.NO_PERMISSION);
    failure.setMessage(
        MessageSourceUtil.getMessage(
            request, messageSource, MessageSourceCode.Account.NO_PERMISSION));
    responseJsonData(response, failure);
  }
}

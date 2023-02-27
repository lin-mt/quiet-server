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
import com.github.quiet.entity.system.QuietUser;
import com.github.quiet.filter.AuthenticationToken;
import com.github.quiet.result.Result;
import com.github.quiet.service.app.CacheService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 退出成功返回 json 数据.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Component
@AllArgsConstructor
public class ResultLogoutSuccessHandler extends AbstractResponseJsonData
    implements LogoutSuccessHandler {

  private final MessageSource messageSource;
  private final ObjectMapper objectMapper;
  private final CacheService cacheService;

  @Override
  public void onLogoutSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    logger.info("用户退出登录成功：{}", authentication);
    Result<Object> success = Result.success();
    String message = getMessage(request, messageSource, MessageSourceCode.Account.LOGOUT_SUCCESS);
    success.setCode(MessageSourceCode.Account.LOGOUT_SUCCESS);
    success.setMessage(message);
    QuietUser quietUser = (QuietUser) authentication.getPrincipal();
    String usernameTokenKey = CacheService.usernameTokenKey(quietUser.getUsername());
    AuthenticationToken token = cacheService.getAndDelete(usernameTokenKey);
    if (token != null) {
      cacheService.remove(CacheService.refreshTokenKey(token.getRefreshToken()));
      cacheService.remove(CacheService.accessTokenKey(token.getAccessToken()));
    }
    responseJsonData(response, success);
  }
}

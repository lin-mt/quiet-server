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
import com.github.quiet.entity.system.QuietUserRole;
import com.github.quiet.filter.AuthenticationToken;
import com.github.quiet.properties.TokenProperties;
import com.github.quiet.result.Result;
import com.github.quiet.service.app.CacheService;
import com.github.quiet.service.system.QuietRoleService;
import com.github.quiet.service.system.QuietUserRoleService;
import com.github.quiet.utils.MessageSourceUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 登陆/认证 成功 Handler.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Component
@RequiredArgsConstructor
public class ResultAuthenticationSuccessHandler extends AbstractResponseJsonData
    implements AuthenticationSuccessHandler {

  private final MessageSource messageSource;
  private final QuietUserRoleService userRoleService;
  private final QuietRoleService roleService;
  private final CacheService cacheService;
  private final TokenProperties properties;
  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    logger.info("用户登录成功：{}", authentication);
    QuietUser quietUser = (QuietUser) authentication.getPrincipal();
    Set<Long> roleIds =
        userRoleService.findByUserId(quietUser.getId()).stream()
            .map(QuietUserRole::getRoleId)
            .collect(Collectors.toSet());
    if (CollectionUtils.isNotEmpty(roleIds)) {
      quietUser.setAuthorities(roleService.findAllByIds(roleIds));
    }
    String usernameTokenKey = CacheService.usernameTokenKey(quietUser.getUsername());
    AuthenticationToken token = cacheService.get(usernameTokenKey);
    if (token == null) {
      token = buildAuthenticationToken(quietUser);
      cacheService.set(usernameTokenKey, token, Duration.ofSeconds(token.getExpiresIn()));
      cacheService.set(
          CacheService.refreshTokenKey(token.getRefreshToken()),
          token,
          Duration.ofSeconds(token.getRefreshExpiresIn()));
      cacheService.set(
          CacheService.accessTokenKey(token.getAccessToken()),
          quietUser,
          Duration.ofSeconds(token.getExpiresIn()));
    } else {
      Long expire = cacheService.getExpire(usernameTokenKey);
      token.setRefreshExpiresIn(token.getRefreshExpiresIn() - token.getExpiresIn() + expire);
      token.setExpiresIn(expire);
    }
    Result<Object> success = Result.success();
    success.setCode(MessageSourceCode.Account.LOGIN_SUCCESS);
    success.setData(token);
    success.setMessage(
        MessageSourceUtil.getMessage(
            request, messageSource, MessageSourceCode.Account.LOGIN_SUCCESS));
    responseJsonData(response, success);
  }

  private AuthenticationToken buildAuthenticationToken(QuietUser quietUser) {
    AuthenticationToken authenticationToken = new AuthenticationToken();
    authenticationToken.setAccessToken(UUID.randomUUID().toString().replace("-", ""));
    authenticationToken.setRefreshToken(UUID.randomUUID().toString().replace("-", ""));
    authenticationToken.setExpiresIn(properties.getTokenExpiresIn().getSeconds());
    authenticationToken.setRefreshExpiresIn(properties.getRefreshExpiresIn().getSeconds());
    authenticationToken.setUser(quietUser);
    return authenticationToken;
  }
}

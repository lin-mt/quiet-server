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

package com.github.quiet.controller.system;

import com.github.quiet.entity.system.QuietUser;
import com.github.quiet.filter.AuthenticationToken;
import com.github.quiet.properties.TokenProperties;
import com.github.quiet.result.Result;
import com.github.quiet.service.app.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

  private final CacheService cacheService;
  private final TokenProperties properties;

  @GetMapping("/refresh")
  public Result<AuthenticationToken> refresh(
      @RequestParam(value = "refresh_token") String refreshToken) {
    AuthenticationToken token =
        cacheService.getAndDelete(CacheService.refreshTokenKey(refreshToken));
    if (token != null) {
      // 删除旧缓存
      QuietUser user = token.getUser();
      String usernameTokenKey = CacheService.usernameTokenKey(user.getUsername());
      cacheService.remove(usernameTokenKey);
      cacheService.remove(CacheService.accessTokenKey(token.getAccessToken()));
      token.setAccessToken(UUID.randomUUID().toString().replace("-", ""));
      token.setRefreshToken(UUID.randomUUID().toString().replace("-", ""));
      token.setExpiresIn(properties.getTokenExpiresIn().getSeconds());
      token.setRefreshExpiresIn(properties.getRefreshExpiresIn().getSeconds());
      cacheService.set(usernameTokenKey, token, Duration.ofSeconds(token.getExpiresIn()));
      cacheService.set(
          CacheService.refreshTokenKey(token.getRefreshToken()),
          token,
          Duration.ofSeconds(token.getRefreshExpiresIn()));
      cacheService.set(
          CacheService.accessTokenKey(token.getAccessToken()),
          token.getUser(),
          Duration.ofSeconds(token.getExpiresIn()));
      return Result.success(token);
    } else {
      return Result.failure();
    }
  }
}

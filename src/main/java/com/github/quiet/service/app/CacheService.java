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

package com.github.quiet.service.app;

import java.time.Duration;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface CacheService {

  static String refreshTokenKey(String token) {
    return String.format("refresh_token:%s", token);
  }

  static String accessTokenKey(String token) {
    return String.format("access_token:%s", token);
  }

  static String usernameTokenKey(String username) {
    return String.format("username_token:%s", username);
  }

  void set(String key, Object value);

  void set(String key, Object value, Duration time);

  <T> T get(String key);

  <T> T getAndDelete(String key);

  Long getExpire(String key);

  void expire(String key, Duration time);

  void remove(String key);
}

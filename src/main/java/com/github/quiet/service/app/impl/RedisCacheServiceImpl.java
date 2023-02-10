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

package com.github.quiet.service.app.impl;

import com.github.quiet.service.app.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Component
@RequiredArgsConstructor
public class RedisCacheServiceImpl implements CacheService {

  private final RedisTemplate<String, Object> redisTemplate;

  @Override
  public void set(String key, Object value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public void set(String key, Object value, Duration time) {
    redisTemplate.opsForValue().set(key, value, time);
  }

  @Override
  public <T> T get(String key) {
    //noinspection unchecked
    return (T) redisTemplate.opsForValue().get(key);
  }

  @Override
  public <T> T getAndDelete(String key) {
    //noinspection unchecked
    return (T) redisTemplate.opsForValue().getAndDelete(key);
  }

  @Override
  public Long getExpire(String key) {
    return redisTemplate.getExpire(key);
  }

  @Override
  public void expire(String key, Duration time) {
    redisTemplate.expire(key, time);
  }

  @Override
  public void remove(String key) {
    redisTemplate.delete(key);
  }
}

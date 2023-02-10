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

package com.github.quiet.config;

import com.github.quiet.properties.WebConfigProperties;
import com.github.quiet.properties.WebConfigProperties.ControllerPrefixConfig;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WebConfigProperties.class)
public class WebConfig implements WebMvcConfigurer {

  private final WebConfigProperties properties;

  @Override
  public void configurePathMatch(@NotNull PathMatchConfigurer configurer) {
    AntPathMatcher antPathMatcher = new AntPathMatcher(".");
    Set<ControllerPrefixConfig> prefixConfigs = properties.getControllerPrefixConfigs();
    if (CollectionUtils.isNotEmpty(prefixConfigs)) {
      for (ControllerPrefixConfig config : prefixConfigs) {
        configurer.addPathPrefix(
            config.getPrefix(),
            clazz -> antPathMatcher.match(config.getPattern(), clazz.getPackageName()));
      }
    }
  }
}

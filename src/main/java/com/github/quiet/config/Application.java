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

import com.github.quiet.converter.StringToDictConverter;
import com.github.quiet.filter.QueryParamSnakeCaseFilter;
import com.github.quiet.utils.SpringUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
public class Application {

  @Bean
  public SpringUtil springUtil() {
    return new SpringUtil();
  }

  @Bean
  public StringToDictConverter stringToDictConverter() {
    return new StringToDictConverter();
  }

  @Bean
  @ConditionalOnProperty(
      prefix = "spring.jackson",
      name = "property-naming-strategy",
      havingValue = "SNAKE_CASE")
  public FilterRegistrationBean<QueryParamSnakeCaseFilter> getMethodQueryParamSnakeCaseFilter() {
    FilterRegistrationBean<QueryParamSnakeCaseFilter> registrationBean =
        new FilterRegistrationBean<>();
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    registrationBean.setFilter(new QueryParamSnakeCaseFilter());
    registrationBean.addUrlPatterns("/*");
    return registrationBean;
  }
}

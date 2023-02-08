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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.quiet.constant.service.Url;
import com.github.quiet.filter.AuthenticationFilter;
import com.github.quiet.handler.AuthenticationJsonEntryPointHandler;
import com.github.quiet.handler.ResultAccessDeniedHandler;
import com.github.quiet.handler.ResultAuthenticationFailureHandler;
import com.github.quiet.handler.ResultAuthenticationSuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@AllArgsConstructor
@EnableMethodSecurity
public class Security {

  private final ResultAccessDeniedHandler accessDeniedHandler;
  private final LogoutHandler logoutHandler;
  private final AuthenticationJsonEntryPointHandler authenticationJsonEntryPointHandler;
  private final LogoutSuccessHandler logoutSuccessHandler;
  private final ResultAuthenticationSuccessHandler successHandler;
  private final ResultAuthenticationFailureHandler failureHandler;
  private final ObjectMapper objectMapper;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // @formatter:off
    http.csrf()
        .disable()
        .authorizeHttpRequests()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationJsonEntryPointHandler)
        .accessDeniedHandler(accessDeniedHandler)
        .and()
        .logout()
        .logoutUrl(Url.LOGOUT)
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler(logoutSuccessHandler);
    AuthenticationFilter loginByAccountFilter =
        new AuthenticationFilter(successHandler, failureHandler, objectMapper);
    // @formatter:on
    http.addFilterAt(loginByAccountFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}

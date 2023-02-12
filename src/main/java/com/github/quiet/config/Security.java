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
import com.github.quiet.filter.AuthenticationTokenFilter;
import com.github.quiet.filter.JsonAuthenticationFilter;
import com.github.quiet.handler.AuthenticationJsonEntryPointHandler;
import com.github.quiet.handler.ResultAccessDeniedHandler;
import com.github.quiet.handler.ResultAuthenticationFailureHandler;
import com.github.quiet.handler.ResultAuthenticationSuccessHandler;
import com.github.quiet.properties.TokenProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(TokenProperties.class)
public class Security {

  private final AuthenticationTokenFilter authenticationTokenFilter;
  private final ResultAccessDeniedHandler accessDeniedHandler;
  private final AuthenticationJsonEntryPointHandler authenticationJsonEntryPointHandler;
  private final LogoutHandler logoutHandler;
  private final LogoutSuccessHandler logoutSuccessHandler;
  private final ResultAuthenticationSuccessHandler successHandler;
  private final ResultAuthenticationFailureHandler failureHandler;
  private final ObjectMapper objectMapper;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain quietSecurityFilterChain(
      HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
    AuthenticationManager manager = authenticationConfiguration.getAuthenticationManager();
    JsonAuthenticationFilter filter =
        new JsonAuthenticationFilter(successHandler, failureHandler, manager, objectMapper);
    http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationJsonEntryPointHandler)
        .accessDeniedHandler(accessDeniedHandler)
        .and()
        .logout()
        .logoutUrl(Url.LOGOUT)
        .addLogoutHandler(logoutHandler)
        .logoutSuccessHandler(logoutSuccessHandler);
    http.authorizeHttpRequests()
        .requestMatchers("/login", "/token/refresh", "/minio/**")
        .permitAll()
        .anyRequest()
        .authenticated();
    return http.build();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(
      UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }
}

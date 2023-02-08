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

package com.github.quiet.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.quiet.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.github.quiet.constant.service.MessageSourceCode.UNKNOWN_CODE;

/**
 * Result 统一处理.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Slf4j
@RestControllerAdvice
public class ResultResponseBodyAdvice<T> implements ResponseBodyAdvice<Result<T>> {

  private final MessageSource messageSource;
  private final ObjectMapper objectMapper;

  public ResultResponseBodyAdvice(MessageSource messageSource, ObjectMapper objectMapper) {
    this.messageSource = messageSource;
    this.objectMapper = objectMapper;
  }

  @Override
  public boolean supports(
      final MethodParameter methodParameter,
      @NonNull final Class<? extends HttpMessageConverter<?>> aClass) {
    return Result.class.isAssignableFrom(methodParameter.getParameterType());
  }

  @Override
  public Result<T> beforeBodyWrite(
      final Result<T> result,
      @NonNull final MethodParameter methodParameter,
      @NonNull final MediaType mediaType,
      @NonNull final Class<? extends HttpMessageConverter<?>> aClass,
      @NonNull final ServerHttpRequest serverHttpRequest,
      @NonNull final ServerHttpResponse serverHttpResponse) {
    if (Objects.nonNull(result)) {
      fillMessage(result, serverHttpRequest);
    }
    try {
      log.info("result data: {}", objectMapper.writeValueAsString(result));
    } catch (JsonProcessingException e) {
      log.error("result to json string error", e);
    }
    return result;
  }

  private void fillMessage(Result<T> result, ServerHttpRequest serverHttpRequest) {
    if (StringUtils.isBlank(result.getMessage())) {
      if (Objects.isNull(result.getCode()) && result.getCurdType() != null) {
        result.setCode(result.getCurdType().getCode());
      }
      if (StringUtils.isNoneBlank(result.getCode()) && StringUtils.isBlank(result.getMessage())) {
        List<Locale> locales = Collections.emptyList();
        try {
          locales = serverHttpRequest.getHeaders().getAcceptLanguageAsLocales();
        } catch (IllegalArgumentException ignore) {
        }
        String message = null;
        try {
          if (!locales.isEmpty()) {
            for (Locale locale : locales) {
              message = getMessage(locale, result.getCode(), result.getMsgParam());
              if (StringUtils.isNoneBlank(message)) {
                break;
              }
            }
          }
          if (StringUtils.isBlank(message)) {
            message = getMessage(Locale.getDefault(), result.getCode(), result.getMsgParam());
          }
        } catch (NoSuchMessageException ignored) {
        }
        if (StringUtils.isNoneBlank(message)) {
          result.setMessage(message);
        }
      }
    }
  }

  private String getMessage(Locale locale, String code, Object... param) {
    String message;
    try {
      message = messageSource.getMessage(code, param, locale);
    } catch (NoSuchMessageException e) {
      message = messageSource.getMessage(UNKNOWN_CODE, new Object[] {code}, locale);
    }
    return message;
  }
}

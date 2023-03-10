/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.quiet.controller.doc;

import com.github.quiet.entity.doc.DocProjectEnv;
import com.github.quiet.service.doc.DocProjectEnvService;
import com.github.quiet.utils.JsonUtils;
import com.google.common.io.ByteStreams;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/request")
public class RequestController {

  private static final String DEFAULT_HOSTNAME = "http://127.0.0.1:9363";

  private static final String REMOVED_FORMAT = "/request/%s";

  private static final Long NONE_SELECTED_ENVIRONMENT_ID = 0L;

  private final OkHttpClient client = new OkHttpClient();

  private final DocProjectEnvService projectEnvService;

  @RequestMapping("/{envId}/**")
  public void request(
      final HttpServletRequest servletRequest,
      final HttpServletResponse servletResponse,
      @PathVariable Long envId)
      throws IOException {
    RequestBody requestBody;
    String contentType = servletRequest.getContentType();
    MediaType mediaType = null;
    if (contentType != null) {
      mediaType = MediaType.parse(contentType);
    }
    try {
      if (servletRequest instanceof MultipartHttpServletRequest) {
        Collection<Part> parts = servletRequest.getParts();
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        if (CollectionUtils.isNotEmpty(parts)) {
          for (Part part : parts) {
            String partContentType = part.getContentType();
            InputStream inputStream = part.getInputStream();
            byte[] requestBodyBytes = ByteStreams.toByteArray(inputStream);
            MediaType partMediaType;
            RequestBody partRequestBody;
            if (partContentType != null) {
              partMediaType = MediaType.parse(partContentType);
              partRequestBody = RequestBody.create(requestBodyBytes, partMediaType);
            } else {
              partRequestBody = RequestBody.create(requestBodyBytes);
            }
            MultipartBody.Part partBody =
                MultipartBody.Part.createFormData(
                    part.getName(), part.getSubmittedFileName(), partRequestBody);
            multipartBodyBuilder.addPart(partBody);
          }
        }
        if (mediaType != null) {
          multipartBodyBuilder.setType(mediaType);
        }
        requestBody = multipartBodyBuilder.build();
      } else {
        InputStream inputStream = servletRequest.getInputStream();
        byte[] requestBodyBytes = ByteStreams.toByteArray(inputStream);
        requestBody = RequestBody.create(requestBodyBytes, mediaType);
      }
      String hostname;
      if (!NONE_SELECTED_ENVIRONMENT_ID.equals(envId)) {
        DocProjectEnv env = projectEnvService.getById(envId);
        hostname = env.getProtocol().getValue() + env.getDomain();
      } else {
        hostname = DEFAULT_HOSTNAME;
      }
      String path =
          servletRequest.getRequestURI().substring(String.format(REMOVED_FORMAT, envId).length());
      if (StringUtils.isNotBlank(servletRequest.getQueryString())) {
        path = path + "?" + servletRequest.getQueryString();
      }
      Request.Builder builder =
          getBuilderByMethod(hostname + path, servletRequest.getMethod(), requestBody);
      Enumeration<String> headerNames = servletRequest.getHeaderNames();
      while (headerNames.hasMoreElements()) {
        String name = headerNames.nextElement();
        builder.addHeader(name, servletRequest.getHeader(name));
      }
      Request request = builder.build();
      try (Response response = client.newCall(request).execute()) {
        servletResponse.reset();
        Map<String, String> originHeaders = new HashMap<>();
        for (String name : response.headers().names()) {
          servletResponse.setHeader(name, response.header(name));
          originHeaders.put(name, response.header(name));
        }
        servletResponse.setHeader("origin-headers", JsonUtils.toJsonString(originHeaders));
        servletResponse.setStatus(response.code());
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
          servletResponse.getOutputStream().write(responseBody.byteStream().readAllBytes());
        }
      }
    } catch (IOException | ServletException exception) {
      log.error("????????????", exception);
      servletResponse.setStatus(500);
      servletResponse
          .getOutputStream()
          .write(JsonUtils.toJsonString(exception).getBytes(StandardCharsets.UTF_8));
    }
  }

  private Request.Builder getBuilderByMethod(String url, String method, RequestBody requestBody) {
    Request.Builder builder = new Request.Builder().url(url);
    if ("GET".equalsIgnoreCase(method)) {
      builder.get();
    } else if ("POST".equalsIgnoreCase(method)) {
      builder.post(requestBody);
    } else if ("DELETE".equalsIgnoreCase(method)) {
      builder.delete(requestBody);
    } else if ("PATCH".equalsIgnoreCase(method)) {
      builder.patch(requestBody);
    } else if ("PUT".equalsIgnoreCase(method)) {
      builder.put(requestBody);
    } else {
      builder.method(method, requestBody);
    }
    return builder;
  }
}

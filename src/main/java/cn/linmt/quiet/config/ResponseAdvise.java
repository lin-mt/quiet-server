package cn.linmt.quiet.config;

import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.modal.http.Response;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.util.CurrentUser;
import cn.linmt.quiet.util.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "cn.linmt.quiet")
public class ResponseAdvise implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(
      @NonNull MethodParameter returnType,
      @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(
      Object body,
      @NonNull MethodParameter returnType,
      @NonNull MediaType selectedContentType,
      @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
      @NonNull ServerHttpRequest request,
      @NonNull ServerHttpResponse response) {
    if (body instanceof Response<?>) {
      return body;
    }
    if (body instanceof String) {
      response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
      return JsonUtils.toString(Response.ok(body));
    }
    return Response.ok(body);
  }

  @ExceptionHandler(BizException.class)
  public Response<Object> exceptionHandler(BizException exception) {
    Result result = exception.getResult();
    return Response.fail(result);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public Response<Object> exceptionHandler(AccessDeniedException exception) {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    String url = null;
    String method = null;
    if (requestAttributes != null) {
      HttpServletRequest request =
          ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      url = request.getRequestURL().toString();
      method = request.getMethod();
    }
    log.error("用户{}访问未授权的页面，method={}，url={}", CurrentUser.getUserId(), method, url, exception);
    return Response.fail(Result.ACCESS_DENIED);
  }

  @ExceptionHandler(Exception.class)
  public Response<Object> exceptionHandler(Exception exception) {
    log.error("未处理异常", exception);
    return Response.fail(Result.EXCEPTION);
  }
}

package cn.linmt.quiet.config;

import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.modal.http.MessageType;
import cn.linmt.quiet.modal.http.Response;
import cn.linmt.quiet.util.CurrentUser;
import cn.linmt.quiet.util.JsonUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
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
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = "cn.linmt.quiet")
public class ResponseAdvise implements ResponseBodyAdvice<Object> {

  private static final Map<Integer, MessageType> CODE_MSG_TYPE = new ConcurrentHashMap<>();
  private final MessageSource messageSource;

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
    Integer code = exception.getCode();
    return getFailFromCode(code, exception.getArgs());
  }

  private Response<Object> getFailFromCode(Integer code, Object[] args) {
    String message = messageSource.getMessage(String.valueOf(code), args, Locale.getDefault());
    int index = message.indexOf("_");
    String finalMessage = message;
    MessageType messageType =
        CODE_MSG_TYPE.computeIfAbsent(
            code,
            (key) -> {
              if (index < 0) {
                return MessageType.SILENT;
              }
              String pre = finalMessage.substring(0, index);
              for (MessageType value : MessageType.values()) {
                if (value.name().startsWith(pre)) {
                  return value;
                }
              }
              return MessageType.SILENT;
            });
    if (index > 0) {
      message = message.substring(index + 1);
    }
    return Response.fail(code, message, messageType);
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
    return getFailFromCode(403, null);
  }

  @ExceptionHandler(Exception.class)
  public Response<Object> exceptionHandler(Exception exception) {
    log.error("未处理异常", exception);
    return getFailFromCode(500, null);
  }
}

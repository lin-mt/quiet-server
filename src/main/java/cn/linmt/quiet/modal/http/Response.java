package cn.linmt.quiet.modal.http;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "请求结果")
public class Response<T> {

  @Schema(description = "请求是否成功")
  private final boolean success;

  @Schema(description = "响应编码")
  private final Integer code;

  @Schema(description = "消息")
  private final String message;

  @Schema(description = "消息类型")
  private final MessageType messageType;

  @Schema(description = "返回数据")
  private final T data;

  private Response(boolean success, Result result, T data) {
    this.success = success;
    this.code = result.getCode();
    this.message = result.getMessage();
    this.messageType = result.getMessageType();
    this.data = data;
  }

  public static <T> Response<T> ok(T data) {
    return new Response<>(true, Result.SUCCESS, data);
  }

  public static <T> Response<T> fail(Result result) {
    if (Result.SUCCESS.equals(result)) {
      throw new IllegalArgumentException();
    }
    return new Response<>(false, result, null);
  }
}

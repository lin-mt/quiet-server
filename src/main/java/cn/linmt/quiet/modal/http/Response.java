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

  private Response(boolean success, Integer code, String message, MessageType messageType, T data) {
    this.success = success;
    this.code = code;
    this.message = message;
    this.messageType = messageType;
    this.data = data;
  }

  public static <T> Response<T> ok(T data) {
    return new Response<>(true, 200, "成功", MessageType.SILENT, data);
  }

  public static <T> Response<T> fail(Integer code, String message, MessageType messageType) {
    return new Response<>(false, code, message, messageType, null);
  }
}

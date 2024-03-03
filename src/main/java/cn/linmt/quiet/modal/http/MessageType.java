package cn.linmt.quiet.modal.http;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "消息类型")
public enum MessageType {
  @Schema(description = "不提示")
  SILENT,
  @Schema(description = "警告")
  WARN_MESSAGE,
  @Schema(description = "报错")
  ERROR_MESSAGE,
  @Schema(description = "提示")
  NOTIFICATION,
  @Schema(description = "重定向")
  REDIRECT
}

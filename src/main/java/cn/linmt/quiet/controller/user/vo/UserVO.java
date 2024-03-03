package cn.linmt.quiet.controller.user.vo;

import cn.linmt.quiet.enums.Enabled;
import cn.linmt.quiet.enums.Expired;
import cn.linmt.quiet.enums.Locked;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "用户信息")
public class UserVO {

  @NotNull
  @Schema(description = "用户ID", type = "string")
  private Long id;

  @NotNull
  @NotBlank
  @Schema(description = "用户名")
  private String username;

  @NotNull
  @Schema(description = "账号过期")
  private Expired accountExpired;

  @NotNull
  @Schema(description = "账号未锁定")
  private Locked accountLocked;

  @NotNull
  @Schema(description = "密码未过期")
  private Expired credentialsExpired;

  @NotNull
  @Schema(description = "账号启用")
  private Enabled enabled;

  @NotNull
  @Schema(description = "注册时间")
  private LocalDateTime gmtCreate;
}

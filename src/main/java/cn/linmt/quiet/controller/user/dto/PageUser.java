package cn.linmt.quiet.controller.user.dto;

import cn.linmt.quiet.controller.PageFilter;
import cn.linmt.quiet.enums.Enabled;
import cn.linmt.quiet.enums.Expired;
import cn.linmt.quiet.enums.Locked;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "查询条件")
@EqualsAndHashCode(callSuper = true)
public class PageUser extends PageFilter {
  @Schema(description = "用户ID")
  private Long id;

  @Schema(description = "用户名")
  private String username;

  @Schema(description = "账号过期")
  private Expired accountExpired;

  @Schema(description = "账号锁定")
  private Locked accountLocked;

  @Schema(description = "密码过期")
  private Expired credentialsExpired;

  @Schema(description = "账号启用")
  private Enabled enabled;
}

package cn.linmt.quiet.controller.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Data;

@Data
public class UserRoles {

  @Schema(description = "用户ID")
  private Long userId;

  @Schema(description = "角色ID集合")
  private Set<Long> roleIds;
}

package cn.linmt.quiet.controller.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Data;

@Data
public class RolePermissions {

  @Schema(description = "角色ID")
  private Long roleId;

  @Schema(description = "权限ID集合")
  private Set<Long> permissionIds;
}

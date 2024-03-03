package cn.linmt.quiet.controller.user.vo;

import cn.linmt.quiet.controller.role.vo.RoleInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@Schema(description = "当前用户信息")
public class CurrentUser implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  @Schema(description = "用户ID")
  private Long id;

  @NotBlank
  @Schema(description = "用户名称")
  private String username;

  @Schema(description = "权限信息")
  private UserPermission permission;

  @Schema(description = "用户拥有的角色信息")
  private List<RoleInfo> roles;
}

package cn.linmt.quiet.controller.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Set;
import lombok.Data;

@Data
@Schema(description = "用户权限")
public class UserPermission implements Serializable {

  @Schema(description = "路由权限")
  private Set<String> paths;
}

package cn.linmt.quiet.controller.permission.vo;

import cn.linmt.quiet.enums.HttpMethod;
import cn.linmt.quiet.enums.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
@Schema(description = "权限信息，包含子权限信息")
public class TreePermission {

  @NotNull
  @Schema(description = "权限ID")
  private Long id;

  @NotBlank
  @Schema(description = "权限名称")
  private String name;

  @NotNull
  @Schema(description = "权限类型")
  private PermissionType type;

  @Schema(description = "请求URL")
  private String httpUrl;

  @Schema(description = "请求方法")
  private HttpMethod httpMethod;

  @Schema(description = "权限描述")
  private String description;

  @NotBlank
  @Schema(description = "权限值")
  private String value;

  @Schema(description = "路径")
  private String path;

  @Schema(description = "父权限ID")
  private Long parentId;

  @Schema(description = "子权限信息")
  private List<TreePermission> children;
}

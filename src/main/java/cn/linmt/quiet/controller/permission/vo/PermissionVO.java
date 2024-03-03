package cn.linmt.quiet.controller.permission.vo;

import cn.linmt.quiet.enums.HttpMethod;
import cn.linmt.quiet.enums.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "权限信息")
public class PermissionVO {

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

  @Schema(description = "路径")
  private String path;

  @Schema(description = "请求方法")
  private HttpMethod httpMethod;

  @Schema(description = "备注")
  private String remark;

  @NotBlank
  @Schema(description = "权限值")
  private String value;

  @Min(0)
  @NotNull
  @Schema(description = "序号")
  private int ordinal;

  @Schema(description = "父权限ID")
  private Long parentId;
}

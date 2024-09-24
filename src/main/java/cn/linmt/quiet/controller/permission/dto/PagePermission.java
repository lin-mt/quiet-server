package cn.linmt.quiet.controller.permission.dto;

import cn.linmt.quiet.enums.HttpMethod;
import cn.linmt.quiet.enums.PermissionType;
import cn.linmt.quiet.modal.http.PageFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "查询权限信息")
@EqualsAndHashCode(callSuper = true)
public class PagePermission extends PageFilter {

  @Schema(description = "权限ID")
  private Long id;

  @Schema(description = "权限名称")
  private String name;

  @Schema(description = "权限值")
  private String value;

  @Schema(description = "父权限ID")
  private Long parentId;

  @Schema(description = "权限类型")
  private PermissionType type;

  @Schema(description = "请求URL")
  private String httpUrl;

  @Schema(description = "请求方法")
  private HttpMethod httpMethod;

  @Schema(description = "权限描述")
  private String description;
}

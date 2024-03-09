package cn.linmt.quiet.controller.role.dto;

import cn.linmt.quiet.modal.http.PageFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(description = "查询角色")
@EqualsAndHashCode(callSuper = true)
public class PageRole extends PageFilter {

  @Schema(description = "角色ID")
  private Long id;

  @Schema(description = "角色名称")
  private String name;

  @Schema(description = "角色值")
  private String value;

  @Schema(description = "角色编码")
  private String code;

  @Schema(description = "父角色ID")
  private Long parentId;
}

package cn.linmt.quiet.controller.project.dto;

import cn.linmt.quiet.enums.BuildTool;
import cn.linmt.quiet.modal.http.PageFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageProjectFilter extends PageFilter {

  @Schema(description = "项目ID")
  private Long id;

  @Schema(description = "项目名称")
  private String name;

  @Schema(description = "构建工具")
  private BuildTool buildTool;

  @Schema(description = "项目描述")
  private String description;
}

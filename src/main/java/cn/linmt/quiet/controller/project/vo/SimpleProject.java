package cn.linmt.quiet.controller.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SimpleProject {

  @Schema(description = "项目ID")
  private Long id;

  @Schema(description = "项目名称")
  private String name;
}

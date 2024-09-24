package cn.linmt.quiet.controller.project.vo;

import cn.linmt.quiet.enums.BuildTool;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProjectVO {

  @NotNull
  @Schema(description = "项目ID")
  private Long id;

  @NotBlank
  @Length(max = 30)
  @Schema(description = "项目名称")
  private String name;

  @NotNull
  @Schema(description = "模板ID")
  private Long templateId;

  @NotNull
  @Schema(description = "项目组ID")
  private Long projectGroupId;

  @NotNull
  @Schema(description = "构建工具")
  private BuildTool buildTool;

  @Schema(description = "项目描述")
  private String description;
}

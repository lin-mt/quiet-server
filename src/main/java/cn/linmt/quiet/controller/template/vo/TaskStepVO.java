package cn.linmt.quiet.controller.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskStepVO {

  @NotNull
  @Schema(description = "步骤ID")
  private Long id;

  @NotBlank
  @Schema(description = "步骤名称")
  private String name;

  @Schema(description = "步骤描述")
  private String description;
}

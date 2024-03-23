package cn.linmt.quiet.controller.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskTypeVO {

  @NotNull
  @Schema(description = "任务类型ID")
  private Long id;

  @NotBlank
  @Schema(description = "任务类型名称")
  private String name;

  @Schema(description = "任务类型描述")
  private String description;
}

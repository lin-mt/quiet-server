package cn.linmt.quiet.controller.iteration.vo;

import cn.linmt.quiet.enums.PlanningStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SimpleIteration {

  @NotNull
  @Schema(description = "迭代ID")
  private Long id;

  @NotBlank
  @Schema(description = "迭代名称")
  private String name;

  @NotNull
  @Schema(description = "迭代状态")
  private PlanningStatus status;
}

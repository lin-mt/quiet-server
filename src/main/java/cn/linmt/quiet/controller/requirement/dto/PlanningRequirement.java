package cn.linmt.quiet.controller.requirement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlanningRequirement {

  @NotNull
  @Schema(description = "需求ID")
  private Long requirementId;

  @Schema(description = "迭代ID")
  private Long iterationId;
}

package cn.linmt.quiet.controller.requirement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ListRequirementTask {

  @NotNull
  @Schema(description = "迭代ID")
  private Long iterationId;

  @Schema(description = "标题")
  private String title;

  @Schema(description = "优先级ID")
  private Long priorityId;

  @Schema(description = "处理人ID")
  private Long handlerId;
}

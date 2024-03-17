package cn.linmt.quiet.controller.iteration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IterationVO extends SimpleIteration {

  @NotNull
  @Schema(description = "版本ID")
  private Long versionId;

  @Schema(description = "计划开始时间")
  private LocalDateTime plannedStartTime;

  @Schema(description = "计划结束时间")
  private LocalDateTime plannedEndTime;

  @Schema(description = "迭代描述")
  private String description;
}

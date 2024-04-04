package cn.linmt.quiet.controller.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateTask extends AddTask {

  @NotNull
  @Schema(description = "任务ID")
  private Long id;
}

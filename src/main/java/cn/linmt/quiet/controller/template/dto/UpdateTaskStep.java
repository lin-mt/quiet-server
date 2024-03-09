package cn.linmt.quiet.controller.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateTaskStep extends AddTaskStep {

  @NotNull
  @Schema(description = "步骤ID")
  private Long id;
}

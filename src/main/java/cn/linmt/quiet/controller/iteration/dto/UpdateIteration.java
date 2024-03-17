package cn.linmt.quiet.controller.iteration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateIteration extends AddIteration {

  @NotNull
  @Schema(description = "迭代ID")
  private Long id;
}

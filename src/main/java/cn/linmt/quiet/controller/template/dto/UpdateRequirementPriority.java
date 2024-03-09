package cn.linmt.quiet.controller.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateRequirementPriority extends AddRequirementPriority {

  @NotNull
  @Schema(description = "优先级ID")
  private Long id;
}

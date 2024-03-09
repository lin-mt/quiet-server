package cn.linmt.quiet.controller.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateTemplate extends TemplateInfo<UpdateTaskStep, UpdateRequirementPriority> {

  @NotNull
  @Schema(description = "模板ID")
  private Long id;
}

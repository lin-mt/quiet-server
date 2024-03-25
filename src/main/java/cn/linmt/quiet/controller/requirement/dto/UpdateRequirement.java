package cn.linmt.quiet.controller.requirement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateRequirement extends AddRequirement {

  @NotNull
  @Schema(description = "需求ID")
  private Long id;
}

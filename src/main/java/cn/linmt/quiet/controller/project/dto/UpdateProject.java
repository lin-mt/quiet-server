package cn.linmt.quiet.controller.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateProject extends AddProject {
  @NotNull
  @Schema(description = "项目ID")
  private Long id;
}

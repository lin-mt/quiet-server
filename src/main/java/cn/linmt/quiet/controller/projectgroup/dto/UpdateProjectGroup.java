package cn.linmt.quiet.controller.projectgroup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateProjectGroup extends AddProjectGroup {

  @NotNull
  @Schema(description = "项目组ID")
  private Long id;
}

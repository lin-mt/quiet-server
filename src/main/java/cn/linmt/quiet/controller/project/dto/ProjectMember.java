package cn.linmt.quiet.controller.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class ProjectMember {

  @NotNull
  @Schema(description = "项目ID")
  private Long projectId;

  @Schema(description = "项目成员ID")
  private Set<Long> memberIds;
}

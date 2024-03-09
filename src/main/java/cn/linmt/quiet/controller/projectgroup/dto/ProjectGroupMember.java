package cn.linmt.quiet.controller.projectgroup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class ProjectGroupMember {

  @NotNull
  @Schema(description = "项目组ID")
  private Long projectGroupId;

  @Schema(description = "成员用户ID集合")
  private Set<Long> memberIds;
}

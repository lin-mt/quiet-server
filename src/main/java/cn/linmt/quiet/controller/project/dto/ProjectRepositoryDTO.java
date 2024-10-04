package cn.linmt.quiet.controller.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectRepositoryDTO {

  @NotNull
  @Schema(description = "仓库ID")
  private Long repositoryId;

  @NotNull
  @Schema(description = "是否自动创建分支")
  private Boolean autoCreateBranch;

  @NotNull
  @Schema(description = "是否自动创建PullRequest")
  private Boolean autoCreatePullRequest;
}

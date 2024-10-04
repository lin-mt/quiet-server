package cn.linmt.quiet.controller.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddProject {

  @NotBlank
  @Length(max = 30)
  @Schema(description = "项目名称")
  private String name;

  @NotNull
  @Schema(description = "模板ID")
  private Long templateId;

  @NotNull
  @Schema(description = "项目组ID")
  private Long projectGroupId;

  @Schema(description = "项目成员ID")
  private Set<Long> memberIds;

  @Schema(description = "代码仓库")
  private List<ProjectRepositoryDTO> repositories;

  @Length(max = 255)
  @Schema(description = "项目描述")
  private String description;
}

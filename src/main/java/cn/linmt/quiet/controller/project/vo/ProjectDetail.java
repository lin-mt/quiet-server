package cn.linmt.quiet.controller.project.vo;

import cn.linmt.quiet.controller.projectgroup.vo.SimpleProjectGroup;
import cn.linmt.quiet.controller.template.vo.SimpleTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectDetail extends ProjectVO {

  @NotNull
  @Schema(description = "所属项目组")
  private SimpleProjectGroup projectGroup;

  @NotNull
  @Schema(description = "项目模板")
  private SimpleTemplate template;

  @Schema(description = "项目成员ID")
  private Set<Long> memberIds;

  @Schema(description = "项目成员")
  private List<Member> members;

  @Schema(description = "项目代码仓库Id集合")
  private Set<Long> repositoryIds;

  @Schema(description = "项目代码仓库")
  private List<ProjectRepositoryVO> repositories;
}

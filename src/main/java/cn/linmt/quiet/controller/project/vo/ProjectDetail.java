package cn.linmt.quiet.controller.project.vo;

import cn.linmt.quiet.controller.projectgroup.vo.SimpleProjectGroup;
import cn.linmt.quiet.controller.template.vo.SimpleTemplate;
import cn.linmt.quiet.enums.BuildTool;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProjectDetail {

  @NotNull
  @Schema(description = "项目ID")
  private Long id;

  @Length(max = 30)
  @Schema(description = "项目名称")
  private String name;

  @NotNull
  @Schema(description = "所属项目组")
  private SimpleProjectGroup projectGroup;

  @NotNull
  @Schema(description = "项目模板")
  private SimpleTemplate template;

  @NotNull
  @Schema(description = "构建工具")
  private BuildTool buildTool;

  @NotBlank
  @Schema(description = "git地址")
  private String gitAddress;

  @Schema(description = "项目成员")
  private List<Member> members;

  @Length(max = 255)
  @Schema(description = "项目描述")
  private String description;

  @NotNull
  @Schema(description = "创建时间")
  private LocalDateTime gmtCreate;
}

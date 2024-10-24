package cn.linmt.quiet.controller.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class UserProject {

  @Schema(description = "项目组ID")
  private Long id;

  @Schema(description = "项目组名")
  private String name;

  private List<SimpleProject> projects;
}

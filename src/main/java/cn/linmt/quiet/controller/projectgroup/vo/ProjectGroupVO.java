package cn.linmt.quiet.controller.projectgroup.vo;

import cn.linmt.quiet.controller.DisabledVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectGroupVO extends DisabledVO {

  @NotNull
  @Schema(description = "项目组ID")
  private Long id;

  @NotBlank
  @Schema(description = "项目组名称")
  private String name;

  @Schema(description = "项目组描述")
  private String description;
}

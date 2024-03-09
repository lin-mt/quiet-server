package cn.linmt.quiet.controller.projectgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SimpleProjectGroup {
  @NotNull
  @Schema(description = "项目组ID")
  private Long id;

  @NotBlank
  @Schema(description = "项目组名称")
  private String name;
}

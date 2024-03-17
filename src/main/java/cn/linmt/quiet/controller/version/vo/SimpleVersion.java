package cn.linmt.quiet.controller.version.vo;

import cn.linmt.quiet.enums.PlanningStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SimpleVersion {

  @NotNull
  @Schema(description = "版本ID")
  private Long id;

  @NotBlank
  @Schema(description = "版本名称")
  private String name;

  @NotNull
  @Schema(description = "版本状态")
  private PlanningStatus status;
}

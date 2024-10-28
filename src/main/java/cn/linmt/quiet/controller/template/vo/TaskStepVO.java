package cn.linmt.quiet.controller.template.vo;

import cn.linmt.quiet.controller.DisabledVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskStepVO extends DisabledVO {

  @NotNull
  @Schema(description = "步骤ID")
  private Long id;

  @NotBlank
  @Schema(description = "步骤名称")
  private String name;

  @Schema(description = "步骤描述")
  private String description;
}

package cn.linmt.quiet.controller.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class TemplateDetail {

  @NotNull
  @Schema(description = "模板ID")
  private Long id;

  @NotBlank
  @Schema(description = "模板名称")
  private String name;

  @Schema(description = "模板描述")
  private String description;

  @NotEmpty
  @Schema(description = "任务步骤")
  private List<TaskStepVO> taskSteps;

  @NotEmpty
  @Schema(description = "任务类型")
  private List<TaskTypeVO> taskTypes;

  @NotEmpty
  @Schema(description = "需求优先级")
  private List<RequirementPriorityVO> requirementPriorities;

  @NotEmpty
  @Schema(description = "需求优类型")
  private List<RequirementTypeVO> requirementTypes;
}

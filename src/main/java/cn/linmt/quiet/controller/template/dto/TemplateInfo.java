package cn.linmt.quiet.controller.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TemplateInfo<
    TS extends AddTaskStep,
    TT extends AddTaskType,
    RP extends AddRequirementPriority,
    RT extends AddRequirementType> {

  @NotBlank
  @Length(max = 30)
  @Schema(description = "模板名称")
  private String name;

  @Length(max = 255)
  @Schema(description = "模板描述")
  private String description;

  @NotEmpty
  @Schema(description = "任务步骤")
  private List<TS> taskSteps;

  @NotEmpty
  @Schema(description = "任务类型")
  private List<TT> taskTypes;

  @NotEmpty
  @Schema(description = "需求优先级")
  private List<RP> requirementPriorities;

  @NotEmpty
  @Schema(description = "需求类型")
  private List<RT> requirementTypes;
}

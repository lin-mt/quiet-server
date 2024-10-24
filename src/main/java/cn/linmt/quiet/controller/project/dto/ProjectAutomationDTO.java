package cn.linmt.quiet.controller.project.dto;

import cn.linmt.quiet.enums.AutomationAction;
import cn.linmt.quiet.enums.RequirementStatus;
import cn.linmt.quiet.enums.TriggerAction;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class ProjectAutomationDTO {

  @NotNull
  @Schema(description = "触发动作")
  private TriggerAction triggerAction;

  @Schema(description = "需求类型ID集合")
  private Set<Long> requirementTypeId;

  @Schema(description = "任务类型ID集合")
  private Set<Long> taskTypeId;

  @Schema(description = "前置任务步骤ID")
  private Long preStepId;

  @Schema(description = "后置任务步骤ID")
  private Long afterStepId;

  @Schema(description = "前置需求状态")
  private RequirementStatus preStatus;

  @Schema(description = "后置需求状态")
  private RequirementStatus afterStatus;

  @Schema(description = "代码仓库ID")
  private Long repositoryId;

  @NotNull
  @Schema(description = "自动化动作")
  private AutomationAction automationAction;
}

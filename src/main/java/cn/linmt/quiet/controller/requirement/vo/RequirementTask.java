package cn.linmt.quiet.controller.requirement.vo;

import cn.linmt.quiet.controller.task.vo.TaskVO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RequirementTask extends RequirementVO {

  @Schema(description = "需求任务")
  private Map<Long, List<TaskVO>> tasks;
}

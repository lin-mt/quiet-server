package cn.linmt.quiet.manager;

import cn.linmt.quiet.entity.RequirementPriority;
import cn.linmt.quiet.entity.TaskStep;
import cn.linmt.quiet.entity.Template;
import cn.linmt.quiet.service.RequirementPriorityService;
import cn.linmt.quiet.service.TaskStepService;
import cn.linmt.quiet.service.TemplateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TemplateManager {

  private final TemplateService templateService;
  private final TaskStepService taskStepService;
  private final RequirementPriorityService requirementPriorityService;

  @Transactional(rollbackFor = Exception.class)
  public Long save(
      Template template, List<TaskStep> taskSteps, List<RequirementPriority> priorities) {
    Long templateId = templateService.save(template);
    taskStepService.saveAll(templateId, taskSteps);
    requirementPriorityService.saveAll(templateId, priorities);
    return templateId;
  }
}

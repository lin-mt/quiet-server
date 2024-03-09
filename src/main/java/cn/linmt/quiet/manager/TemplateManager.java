package cn.linmt.quiet.manager;

import cn.linmt.quiet.entity.Project;
import cn.linmt.quiet.entity.RequirementPriority;
import cn.linmt.quiet.entity.TaskStep;
import cn.linmt.quiet.entity.Template;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.service.ProjectService;
import cn.linmt.quiet.service.RequirementPriorityService;
import cn.linmt.quiet.service.TaskStepService;
import cn.linmt.quiet.service.TemplateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TemplateManager {

  private final TemplateService templateService;
  private final TaskStepService taskStepService;
  private final ProjectService projectService;
  private final RequirementPriorityService requirementPriorityService;

  @Transactional(rollbackFor = Exception.class)
  public Long save(
      Template template, List<TaskStep> taskSteps, List<RequirementPriority> priorities) {
    Long templateId = templateService.save(template);
    taskStepService.saveAll(templateId, taskSteps);
    requirementPriorityService.saveAll(templateId, priorities);
    return templateId;
  }

  @Transactional(rollbackFor = Exception.class)
  public void delete(Long id) {
    List<Project> projects = projectService.listByTemplateId(id);
    if (CollectionUtils.isNotEmpty(projects)) {
      Result.TEMPLATE_CNT_DEL_PROJECT.thr();
    }
    templateService.delete(id);
    taskStepService.deleteByTemplateId(id);
    requirementPriorityService.deleteByTemplateId(id);
  }
}

package cn.linmt.quiet.manager;

import cn.linmt.quiet.entity.*;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.service.*;
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
  private final TaskTypeService taskTypeService;
  private final ProjectService projectService;
  private final RequirementPriorityService requirementPriorityService;
  private final RequirementTypeService requirementTypeService;

  @Transactional(rollbackFor = Exception.class)
  public Long save(
      Template template,
      List<TaskStep> taskSteps,
      List<TaskType> taskTypes,
      List<RequirementPriority> priorities,
      List<RequirementType> requirementTypes) {
    Long templateId = templateService.save(template);
    taskStepService.saveAll(templateId, taskSteps);
    taskTypeService.saveAll(templateId, taskTypes);
    requirementPriorityService.saveAll(templateId, priorities);
    requirementTypeService.saveAll(templateId, requirementTypes);
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
    taskTypeService.deleteByTemplateId(id);
    requirementPriorityService.deleteByTemplateId(id);
    requirementTypeService.deleteByTemplateId(id);
  }
}

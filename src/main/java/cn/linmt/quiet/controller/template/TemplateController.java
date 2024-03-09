package cn.linmt.quiet.controller.template;

import cn.linmt.quiet.controller.template.dto.AddTemplate;
import cn.linmt.quiet.controller.template.dto.TemplateInfo;
import cn.linmt.quiet.controller.template.dto.UpdateTemplate;
import cn.linmt.quiet.controller.template.vo.RequirementPriorityVO;
import cn.linmt.quiet.controller.template.vo.TaskStepVO;
import cn.linmt.quiet.controller.template.vo.TemplateDetail;
import cn.linmt.quiet.entity.RequirementPriority;
import cn.linmt.quiet.entity.TaskStep;
import cn.linmt.quiet.entity.Template;
import cn.linmt.quiet.manager.TemplateManager;
import cn.linmt.quiet.service.RequirementPriorityService;
import cn.linmt.quiet.service.TaskStepService;
import cn.linmt.quiet.service.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/template")
public class TemplateController {

  private final TemplateManager templateManager;
  private final TemplateService templateService;
  private final TaskStepService taskStepService;
  private final RequirementPriorityService requirementPriorityService;

  @PostMapping
  @Operation(summary = "添加模板")
  public Long addTemplate(@RequestBody AddTemplate template) {
    return saveTemplate(template);
  }

  @PutMapping
  @Operation(summary = "更新模板")
  public Long updateTemplate(@RequestBody UpdateTemplate template) {
    return saveTemplate(template);
  }

  @GetMapping("/{id}")
  @Operation(summary = "获取模板详情")
  public TemplateDetail getTemplateDetail(@PathVariable Long id) {
    Template template = templateService.getById(id);
    List<TaskStepVO> taskSteps =
        taskStepService.listByTemplateId(id).stream()
            .map(
                taskStep -> {
                  TaskStepVO taskStepVO = new TaskStepVO();
                  BeanUtils.copyProperties(taskStep, taskStepVO);
                  return taskStepVO;
                })
            .toList();
    List<RequirementPriorityVO> priorityVOS =
        requirementPriorityService.listByTemplateId(id).stream()
            .map(
                priority -> {
                  RequirementPriorityVO priorityVO = new RequirementPriorityVO();
                  BeanUtils.copyProperties(priority, priorityVO);
                  return priorityVO;
                })
            .toList();
    TemplateDetail detail = new TemplateDetail();
    BeanUtils.copyProperties(template, detail);
    detail.setTaskSteps(taskSteps);
    detail.setRequirementPriorities(priorityVOS);
    return detail;
  }

  private <T extends TemplateInfo<?, ?>> Long saveTemplate(T template) {
    Template add = new Template();
    BeanUtils.copyProperties(template, add);
    List<TaskStep> taskSteps =
        template.getTaskSteps().stream()
            .map(
                addTaskStep -> {
                  TaskStep taskStep = new TaskStep();
                  BeanUtils.copyProperties(addTaskStep, taskStep);
                  return taskStep;
                })
            .toList();
    List<RequirementPriority> priorities =
        template.getRequirementPriorities().stream()
            .map(
                priority -> {
                  RequirementPriority newPriority = new RequirementPriority();
                  BeanUtils.copyProperties(priority, newPriority);
                  return newPriority;
                })
            .toList();
    return templateManager.save(add, taskSteps, priorities);
  }
}

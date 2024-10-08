package cn.linmt.quiet.controller.template;

import cn.linmt.quiet.controller.template.dto.AddTemplate;
import cn.linmt.quiet.controller.template.dto.TemplateInfo;
import cn.linmt.quiet.controller.template.dto.UpdateTemplate;
import cn.linmt.quiet.controller.template.vo.*;
import cn.linmt.quiet.entity.*;
import cn.linmt.quiet.manager.TemplateManager;
import cn.linmt.quiet.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/template")
public class TemplateController {

  private final TemplateManager templateManager;
  private final TemplateService templateService;
  private final TaskStepService taskStepService;
  private final TaskTypeService taskTypeService;
  private final RequirementPriorityService requirementPriorityService;
  private final RequirementTypeService requirementTypeService;

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

  @DeleteMapping("/{id}")
  @Operation(summary = "删除模板")
  public void deleteTemplate(@PathVariable Long id) {
    templateManager.delete(id);
  }

  @GetMapping("/page")
  @Operation(summary = "分页查询模板")
  public Page<TemplateVO> pageTemplate(PageTemplate pageTemplate) {
    return templateService
        .page(pageTemplate)
        .map(
            template -> {
              TemplateVO vo = new TemplateVO();
              BeanUtils.copyProperties(template, vo);
              return vo;
            });
  }

  @GetMapping("/list")
  @Operation(summary = "查询模板")
  public List<SimpleTemplate> listTemplate(
      @RequestParam(required = false) @Schema(description = "模板名称") String name) {
    return templateService.listTemplate(name).stream()
        .map(
            template -> {
              SimpleTemplate simpleTemplate = new SimpleTemplate();
              simpleTemplate.setId(template.getId());
              simpleTemplate.setName(template.getName());
              return simpleTemplate;
            })
        .toList();
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
    List<TaskTypeVO> taskTypes =
        taskTypeService.listByTemplateId(id).stream()
            .map(
                type -> {
                  TaskTypeVO taskTypeVO = new TaskTypeVO();
                  BeanUtils.copyProperties(type, taskTypeVO);
                  return taskTypeVO;
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
    List<RequirementTypeVO> requirementTypeVOS =
        requirementTypeService.listByTemplateId(id).stream()
            .map(
                type -> {
                  RequirementTypeVO typeVO = new RequirementTypeVO();
                  BeanUtils.copyProperties(type, typeVO);
                  return typeVO;
                })
            .toList();
    TemplateDetail detail = new TemplateDetail();
    BeanUtils.copyProperties(template, detail);
    detail.setTaskSteps(taskSteps);
    detail.setTaskTypes(taskTypes);
    detail.setRequirementPriorities(priorityVOS);
    detail.setRequirementTypes(requirementTypeVOS);
    return detail;
  }

  private <T extends TemplateInfo<?, ?, ?, ?>> Long saveTemplate(T template) {
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
    List<TaskType> taskTypes =
        template.getTaskTypes().stream()
            .map(
                type -> {
                  TaskType taskType = new TaskType();
                  BeanUtils.copyProperties(type, taskType);
                  return taskType;
                })
            .toList();
    List<RequirementType> requirementTypes =
        template.getRequirementTypes().stream()
            .map(
                type -> {
                  RequirementType requirementType = new RequirementType();
                  BeanUtils.copyProperties(type, requirementType);
                  return requirementType;
                })
            .toList();
    return templateManager.save(add, taskSteps, taskTypes, priorities, requirementTypes);
  }
}

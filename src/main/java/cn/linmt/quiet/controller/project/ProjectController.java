package cn.linmt.quiet.controller.project;

import cn.linmt.quiet.controller.project.dto.AddProject;
import cn.linmt.quiet.controller.project.dto.PageProjectFilter;
import cn.linmt.quiet.controller.project.dto.ProjectAutomationDTO;
import cn.linmt.quiet.controller.project.dto.UpdateProject;
import cn.linmt.quiet.controller.project.vo.ProjectDetail;
import cn.linmt.quiet.controller.project.vo.ProjectVO;
import cn.linmt.quiet.controller.project.vo.UserProject;
import cn.linmt.quiet.entity.Project;
import cn.linmt.quiet.entity.ProjectAutomation;
import cn.linmt.quiet.enums.TriggerAction;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.manager.ProjectManager;
import cn.linmt.quiet.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

  private final ProjectService projectService;
  private final ProjectManager projectManager;

  private <T extends AddProject> Long saveProject(T project) {
    Project save = new Project();
    BeanUtils.copyProperties(project, save);
    List<ProjectAutomationDTO> automations = project.getAutomations();
    List<ProjectAutomation> projectAutomations = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(automations)) {
      projectAutomations.addAll(toProjectAutomation(automations));
    }
    return projectManager.save(save, project.getMemberIds(), projectAutomations);
  }

  private List<ProjectAutomation> toProjectAutomation(List<ProjectAutomationDTO> automations) {
    if (CollectionUtils.isEmpty(automations)) {
      return new ArrayList<>();
    }
    return automations.stream()
        .map(
            automation -> {
              ProjectAutomation projectAutomation = new ProjectAutomation();
              TriggerAction triggerAction = automation.getTriggerAction();
              if (triggerAction.isRequireRequirementType()
                  && CollectionUtils.isEmpty(automation.getRequirementTypeIds())) {
                throw new BizException(118001);
              }
              if (triggerAction.isRequireRequirementStatus()
                  && ObjectUtils.anyNull(
                      automation.getPreRequirementStatus(),
                      automation.getAfterRequirementStatus())) {
                throw new BizException(118002);
              }
              if (triggerAction.isRequireTaskType()
                  && CollectionUtils.isEmpty(automation.getTaskTypeIds())) {
                throw new BizException(118003);
              }
              if (triggerAction.isRequireTaskStep()
                  && ObjectUtils.anyNull(
                      automation.getPreTaskStepId(), automation.getAfterTaskStepId())) {
                throw new BizException(118004);
              }
              if (automation.getAutomationAction().isRelateRepository()
                  && CollectionUtils.isEmpty(automation.getRepositoryIds())) {
                throw new BizException(118005);
              }
              BeanUtils.copyProperties(automation, projectAutomation);
              return projectAutomation;
            })
        .toList();
  }

  @PostMapping
  @Operation(summary = "添加项目")
  public Long addProject(@RequestBody @Validated AddProject project) {
    return saveProject(project);
  }

  @PutMapping
  @Operation(summary = "更新项目")
  public Long updateProject(@RequestBody @Validated UpdateProject project) {
    return saveProject(project);
  }

  @GetMapping("/page")
  @Operation(summary = "分页查询项目信息")
  public Page<ProjectVO> pageProject(@Validated PageProjectFilter pageProjectFilter) {
    Page<Project> projects = projectService.page(pageProjectFilter);
    return projects.map(
        project -> {
          ProjectVO projectVO = new ProjectVO();
          BeanUtils.copyProperties(project, projectVO);
          return projectVO;
        });
  }

  @GetMapping("/{id}")
  @Operation(summary = "项目详情")
  public ProjectDetail getProjectDetail(@PathVariable Long id) {
    return projectManager.detail(id);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除项目")
  public void deleteProject(@PathVariable Long id) {
    projectManager.delete(id);
  }

  @GetMapping("/listCurrentUserProject")
  @Operation(summary = "获取当前用户的项目")
  public List<UserProject> listCurrentUserProject() {
    return projectManager.listCurrentUserProject();
  }
}

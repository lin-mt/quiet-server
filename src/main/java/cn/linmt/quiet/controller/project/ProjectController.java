package cn.linmt.quiet.controller.project;

import cn.linmt.quiet.controller.project.dto.AddProject;
import cn.linmt.quiet.controller.project.dto.PageProjectFilter;
import cn.linmt.quiet.controller.project.dto.UpdateProject;
import cn.linmt.quiet.controller.project.vo.ProjectDetail;
import cn.linmt.quiet.controller.project.vo.ProjectVO;
import cn.linmt.quiet.controller.project.vo.UserProject;
import cn.linmt.quiet.entity.Project;
import cn.linmt.quiet.manager.ProjectManager;
import cn.linmt.quiet.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

  @PostMapping
  @Operation(summary = "添加项目")
  public Long addProject(@RequestBody @Validated AddProject project) {
    Project save = new Project();
    BeanUtils.copyProperties(project, save);
    return projectManager.save(save, project.getMemberIds());
  }

  @PutMapping
  @Operation(summary = "更新项目")
  public Long updateProject(@RequestBody @Validated UpdateProject project) {
    Project update = new Project();
    BeanUtils.copyProperties(project, update);
    return projectManager.save(update, project.getMemberIds());
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

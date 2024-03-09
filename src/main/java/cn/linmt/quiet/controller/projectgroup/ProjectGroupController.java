package cn.linmt.quiet.controller.projectgroup;

import cn.linmt.quiet.controller.projectgroup.dto.AddProjectGroup;
import cn.linmt.quiet.controller.projectgroup.dto.PageProjectGroup;
import cn.linmt.quiet.controller.projectgroup.dto.ProjectGroupMember;
import cn.linmt.quiet.controller.projectgroup.dto.UpdateProjectGroup;
import cn.linmt.quiet.controller.projectgroup.vo.ProjectGroupDetail;
import cn.linmt.quiet.controller.projectgroup.vo.ProjectGroupVO;
import cn.linmt.quiet.controller.projectgroup.vo.SimpleProjectGroup;
import cn.linmt.quiet.controller.user.vo.SimpleUser;
import cn.linmt.quiet.entity.ProjectGroup;
import cn.linmt.quiet.manager.ProjectGroupManager;
import cn.linmt.quiet.service.ProjectGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projectGroup")
public class ProjectGroupController {

  private final ProjectGroupService projectGroupService;
  private final ProjectGroupManager projectGroupManager;

  @PostMapping
  @Operation(summary = "新增项目组信息")
  public Long addProjectGroup(@RequestBody AddProjectGroup projectGroup) {
    ProjectGroup add = new ProjectGroup();
    BeanUtils.copyProperties(projectGroup, add);
    return projectGroupService.save(add);
  }

  @PutMapping
  @Operation(summary = "更新项目组信息")
  public Long updateProjectGroup(@RequestBody UpdateProjectGroup projectGroup) {
    ProjectGroup update = new ProjectGroup();
    BeanUtils.copyProperties(projectGroup, update);
    return projectGroupService.save(update);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除项目组信息")
  public void deleteProjectGroup(@PathVariable Long id) {
    projectGroupManager.delete(id);
  }

  @GetMapping("/page")
  @Operation(summary = "分页查询项目组信息")
  public Page<ProjectGroupVO> pageProjectGroup(PageProjectGroup page) {
    Page<ProjectGroup> groups = projectGroupService.page(page);
    return groups.map(
        projectGroup -> {
          ProjectGroupVO vo = new ProjectGroupVO();
          BeanUtils.copyProperties(projectGroup, vo);
          return vo;
        });
  }

  @PutMapping("/members")
  @Operation(summary = "更新项目组成员")
  public void updateProjectGroupMembers(@RequestBody ProjectGroupMember member) {
    projectGroupManager.saveMembers(member);
  }

  @GetMapping("/{id}")
  @Operation(summary = "项目组详情")
  public ProjectGroupDetail getProjectGroupDetail(@PathVariable Long id) {
    return projectGroupManager.detail(id);
  }

  @Operation(summary = "根据用户名查询用户（10条数据）")
  @GetMapping("/listProjectGroupUser")
  public List<SimpleUser> listProjectGroupUser(
      @RequestParam Long projectGroupId,
      @RequestParam @Schema(description = "用户名") String username) {
    return projectGroupManager.listGroupUser(projectGroupId, username, 10L);
  }

  @Operation(summary = "查询当前登录人所属项目组")
  @GetMapping("/listCurrentUserProjectGroup")
  public List<SimpleProjectGroup> listCurrentUserProjectGroup() {
    return projectGroupManager.listCurrentUserProjectGroup().stream()
        .map(
            projectGroup -> {
              SimpleProjectGroup group = new SimpleProjectGroup();
              group.setId(projectGroup.getId());
              group.setName(projectGroup.getName());
              return group;
            })
        .toList();
  }
}

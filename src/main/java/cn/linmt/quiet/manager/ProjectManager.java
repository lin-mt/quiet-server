package cn.linmt.quiet.manager;

import cn.linmt.quiet.controller.project.vo.Member;
import cn.linmt.quiet.controller.project.vo.ProjectDetail;
import cn.linmt.quiet.controller.project.vo.SimpleProject;
import cn.linmt.quiet.controller.projectgroup.vo.SimpleProjectGroup;
import cn.linmt.quiet.controller.template.vo.SimpleTemplate;
import cn.linmt.quiet.entity.*;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.service.*;
import cn.linmt.quiet.util.CurrentUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectManager {

  private final ProjectService projectService;
  private final ProjectUserService projectUserService;
  private final UserService userService;
  private final JPAQueryFactory queryFactory;
  private final TemplateService templateService;
  private final ProjectGroupService projectGroupService;

  public ProjectDetail detail(Long id) {
    Project project = projectService.getById(id);
    ProjectDetail detail = new ProjectDetail();
    BeanUtils.copyProperties(project, detail);
    ProjectGroup projectGroup = projectGroupService.getById(project.getProjectGroupId());
    SimpleProjectGroup group = new SimpleProjectGroup();
    group.setId(projectGroup.getId());
    group.setName(projectGroup.getName());
    detail.setProjectGroup(group);
    Template template = templateService.getById(project.getTemplateId());
    SimpleTemplate simpleTemplate = new SimpleTemplate();
    simpleTemplate.setId(template.getId());
    simpleTemplate.setName(template.getName());
    detail.setTemplate(simpleTemplate);
    Set<Long> memberIds = projectUserService.listUserIds(id);
    if (CollectionUtils.isNotEmpty(memberIds)) {
      List<User> users = userService.listById(memberIds);
      List<Member> members = new ArrayList<>();
      for (User user : users) {
        Member member = new Member();
        member.setId(user.getId());
        member.setUsername(user.getUsername());
        members.add(member);
      }
      detail.setMembers(members);
    }
    return detail;
  }

  public void delete(Long id) {
    projectService.deleteById(id);
    projectUserService.deleteByProjectId(id);
  }

  public Long save(Project project) {
    Long id = project.getId();
    if (id != null) {
      Project exist = projectService.getById(id);
      if (!exist.getTemplateId().equals(project.getTemplateId())) {
        // TODO 任务移动
      }
      boolean reBuild =
          !exist.getGitAddress().equalsIgnoreCase(project.getGitAddress())
              || !exist.getBuildTool().equals(project.getBuildTool());
      if (reBuild) {
        // TODO 重新构建文档等数据
      }
      // TODO 模板出现变更
      // TODO 构建工具更新则重新构建文档等数据
    }
    return projectService.save(project);
  }

  public List<SimpleProject> listCurrentUserProject(Long projectGroupId) {
    QProject project = QProject.project;
    QProjectGroupUser projectGroupUser = QProjectGroupUser.projectGroupUser;
    BooleanBuilder predicate =
        Where.builder()
            .isIdEq(projectGroupId, projectGroupUser.projectGroupId)
            .and(projectGroupUser.userId.eq(CurrentUser.getUserId()))
            .getPredicate();
    return queryFactory
        .selectFrom(project)
        .leftJoin(projectGroupUser)
        .on(project.projectGroupId.eq(projectGroupUser.projectGroupId))
        .where(predicate)
        .fetch()
        .stream()
        .map(
            p -> {
              SimpleProject simpleProject = new SimpleProject();
              simpleProject.setId(p.getId());
              simpleProject.setName(p.getName());
              return simpleProject;
            })
        .toList();
  }
}

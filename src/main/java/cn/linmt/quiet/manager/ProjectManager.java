package cn.linmt.quiet.manager;

import cn.linmt.quiet.controller.project.vo.*;
import cn.linmt.quiet.controller.projectgroup.vo.SimpleProjectGroup;
import cn.linmt.quiet.controller.template.vo.SimpleTemplate;
import cn.linmt.quiet.entity.*;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.service.*;
import cn.linmt.quiet.util.CurrentUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectManager {

  private final ProjectService projectService;
  private final ProjectUserService projectUserService;
  private final UserService userService;
  private final JPAQueryFactory queryFactory;
  private final TemplateService templateService;
  private final ProjectGroupService projectGroupService;
  private final ProjectGroupUserService projectGroupUserService;
  private final ProjectAutomationService projectAutomationService;

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
    detail.setMemberIds(memberIds);
    List<ProjectAutomation> projectAutomations = projectAutomationService.listByProjectId(id);
    List<ProjectAutomationVO> automations =
        projectAutomations.stream()
            .map(
                a -> {
                  ProjectAutomationVO projectAutomationVO = new ProjectAutomationVO();
                  BeanUtils.copyProperties(a, projectAutomationVO);
                  return projectAutomationVO;
                })
            .toList();
    detail.setAutomations(automations);
    return detail;
  }

  @Transactional(rollbackFor = Exception.class)
  public void delete(Long id) {
    projectService.deleteById(id);
    projectUserService.deleteByProjectId(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public Long save(
      Project project, Set<Long> memberIds, List<ProjectAutomation> projectAutomations) {
    Long id;
    // TODO 模板出现变更
    // TODO 移动任务
    // TODO 仓库地址变更、重新构建文档等数据
    // TODO 构建工具更新则重新构建文档等数据
    id = projectService.save(project);
    projectUserService.saveProjectMembers(project.getId(), memberIds);
    projectAutomationService.saveProjectAutomations(project.getId(), projectAutomations);
    return id;
  }

  public List<UserProject> listCurrentUserProject() {
    List<ProjectGroupUser> projects = projectGroupUserService.listByUserId(CurrentUser.getUserId());
    Set<Long> projectGroupIds =
        projects.stream().map(ProjectGroupUser::getProjectGroupId).collect(Collectors.toSet());
    if (CollectionUtils.isEmpty(projectGroupIds)) {
      return Collections.emptyList();
    }
    List<ProjectGroup> projectGroups = projectGroupService.listByIds(projectGroupIds);
    QProject project = QProject.project;
    QProjectGroupUser projectGroupUser = QProjectGroupUser.projectGroupUser;
    BooleanBuilder predicate =
        Where.builder()
            .notEmptyIn(projectGroupIds, projectGroupUser.projectGroupId)
            .and(projectGroupUser.userId.eq(CurrentUser.getUserId()))
            .getPredicate();
    Map<Long, List<Project>> projectGroupId2projects =
        queryFactory
            .selectFrom(project)
            .leftJoin(projectGroupUser)
            .on(project.projectGroupId.eq(projectGroupUser.projectGroupId))
            .where(predicate)
            .fetch()
            .stream()
            .collect(Collectors.groupingBy(Project::getProjectGroupId));
    return projectGroups.stream()
        .map(
            projectGroup -> {
              UserProject userProject = new UserProject();
              userProject.setId(projectGroup.getId());
              userProject.setName(projectGroup.getName());
              List<Project> groupProjects =
                  projectGroupId2projects.getOrDefault(projectGroup.getId(), new ArrayList<>());
              userProject.setProjects(
                  groupProjects.stream()
                      .map(
                          p -> {
                            SimpleProject simpleProject = new SimpleProject();
                            simpleProject.setId(p.getId());
                            simpleProject.setName(p.getName());
                            return simpleProject;
                          })
                      .toList());
              return userProject;
            })
        .toList();
  }
}

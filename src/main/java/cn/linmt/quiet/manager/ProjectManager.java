package cn.linmt.quiet.manager;

import cn.linmt.quiet.controller.project.dto.ProjectRepositoryDTO;
import cn.linmt.quiet.controller.project.vo.Member;
import cn.linmt.quiet.controller.project.vo.ProjectDetail;
import cn.linmt.quiet.controller.project.vo.ProjectRepositoryVO;
import cn.linmt.quiet.controller.project.vo.SimpleProject;
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
  private final ProjectRepositoryService projectRepositoryService;
  private final RepositoryService repositoryService;

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
    Set<Long> repositoryIds = new HashSet<>();
    List<ProjectRepositoryVO> repositories =
        projectRepositoryService.listByProjectId(project.getId()).stream()
            .peek(r -> repositoryIds.add(r.getRepositoryId()))
            .map(
                r -> {
                  ProjectRepositoryVO vo = new ProjectRepositoryVO();
                  BeanUtils.copyProperties(r, vo);
                  return vo;
                })
            .toList();
    detail.setRepositoryIds(repositoryIds);
    detail.setRepositories(repositories);
    return detail;
  }

  @Transactional(rollbackFor = Exception.class)
  public void delete(Long id) {
    projectService.deleteById(id);
    projectUserService.deleteByProjectId(id);
    projectRepositoryService.deleteByProjectId(id);
  }

  public Long save(Project project, List<ProjectRepositoryDTO> repositories, Set<Long> memberIds) {
    Long id;
    // TODO 模板出现变更
    // TODO 移动任务
    // TODO 仓库地址变更、重新构建文档等数据
    // TODO 构建工具更新则重新构建文档等数据
    id = projectService.save(project);
    if (CollectionUtils.isNotEmpty(repositories)) {
      Set<Long> repositoryIds =
          repositoryService
              .findAllByIds(
                  repositories.stream().map(ProjectRepositoryDTO::getRepositoryId).toList())
              .stream()
              .map(Repository::getId)
              .collect(Collectors.toSet());
      repositories.removeIf(dto -> !repositoryIds.contains(dto.getRepositoryId()));
      projectRepositoryService.saveAll(project.getId(), repositories);
    }
    projectUserService.updateProjectMembers(project.getId(), memberIds);
    return id;
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

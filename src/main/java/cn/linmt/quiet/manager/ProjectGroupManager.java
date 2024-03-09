package cn.linmt.quiet.manager;

import cn.linmt.quiet.controller.project.vo.Member;
import cn.linmt.quiet.controller.projectgroup.dto.ProjectGroupMember;
import cn.linmt.quiet.controller.projectgroup.vo.ProjectGroupDetail;
import cn.linmt.quiet.controller.user.vo.SimpleUser;
import cn.linmt.quiet.entity.*;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.service.ProjectGroupService;
import cn.linmt.quiet.service.ProjectGroupUserService;
import cn.linmt.quiet.service.ProjectService;
import cn.linmt.quiet.service.UserService;
import cn.linmt.quiet.util.CurrentUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectGroupManager {

  private final ProjectGroupService projectGroupService;
  private final ProjectGroupUserService projectGroupUserService;
  private final JPAQueryFactory queryFactory;
  private final ProjectService projectService;
  private final UserService userService;

  public void delete(Long id) {
    ProjectGroup delete = projectGroupService.getById(id);
    List<Project> projects = projectService.listByGroupId(id);
    if (CollectionUtils.isNotEmpty(projects)) {
      Result.PRO_GROUP_CANT_DELETE_PROJECTS.thr();
    }
    projectGroupUserService.deleteByProjectGroupId(id);
    projectGroupService.deleteById(delete.getId());
  }

  public void saveMembers(ProjectGroupMember member) {
    Long projectGroupId = member.getProjectGroupId();
    if (CollectionUtils.isEmpty(member.getMemberIds())) {
      projectGroupUserService.deleteByProjectGroupId(projectGroupId);
      return;
    }
    List<ProjectGroupUser> users = projectGroupUserService.listByProjectGroupId(projectGroupId);
    if (CollectionUtils.isNotEmpty(users)) {
      List<ProjectGroupUser> removed =
          users.stream()
              .filter(pgUser -> !member.getMemberIds().remove(pgUser.getUserId()))
              .toList();
      projectGroupUserService.delete(removed);
    }
    if (CollectionUtils.isEmpty(member.getMemberIds())) {
      return;
    }
    List<ProjectGroupUser> newUsers = new ArrayList<>();
    for (Long userId : member.getMemberIds()) {
      ProjectGroupUser projectGroupUser = new ProjectGroupUser();
      projectGroupUser.setProjectGroupId(projectGroupId);
      projectGroupUser.setUserId(userId);
      newUsers.add(projectGroupUser);
    }
    projectGroupUserService.saveAll(newUsers);
  }

  public ProjectGroupDetail detail(Long id) {
    ProjectGroup projectGroup = projectGroupService.getById(id);
    ProjectGroupDetail detail = new ProjectGroupDetail();
    Set<Long> members =
        projectGroupUserService.listByProjectGroupId(id).stream()
            .map(ProjectGroupUser::getUserId)
            .collect(Collectors.toSet());
    BeanUtils.copyProperties(projectGroup, detail);
    detail.setMembers(
        userService.listById(members).stream()
            .map(
                user -> {
                  Member member = new Member();
                  member.setId(user.getId());
                  member.setUsername(user.getUsername());
                  return member;
                })
            .toList());
    return detail;
  }

  @Transactional
  public List<SimpleUser> listGroupUser(Long projectGroupId, String username, long limit) {
    QUser user = QUser.user;
    QProjectGroupUser projectGroupUser = QProjectGroupUser.projectGroupUser;
    BooleanBuilder predicate =
        Where.builder()
            .notBlankContains(username, user.username)
            .and(projectGroupUser.projectGroupId.eq(projectGroupId))
            .getPredicate();
    return queryFactory
        .selectFrom(user)
        .leftJoin(projectGroupUser)
        .on(user.id.eq(projectGroupUser.userId))
        .where(predicate)
        .limit(limit)
        .stream()
        .map(
            u -> {
              SimpleUser simpleUser = new SimpleUser();
              simpleUser.setId(u.getId());
              simpleUser.setUsername(u.getUsername());
              return simpleUser;
            })
        .toList();
  }

  public List<ProjectGroup> listCurrentUserProjectGroup() {
    List<ProjectGroupUser> projects = projectGroupUserService.listByUserId(CurrentUser.getUserId());
    Set<Long> projectGroupIds =
        projects.stream().map(ProjectGroupUser::getProjectGroupId).collect(Collectors.toSet());
    return projectGroupService.listByIds(projectGroupIds);
  }
}

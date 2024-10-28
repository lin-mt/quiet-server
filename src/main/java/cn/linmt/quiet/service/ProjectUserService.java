package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.ProjectUser;
import cn.linmt.quiet.repository.ProjectUserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectUserService {

  private final ProjectUserRepository repository;

  public Set<Long> listUserIds(Long projectId) {
    return repository.findByProjectId(projectId).stream()
        .map(ProjectUser::getUserId)
        .collect(Collectors.toSet());
  }

  public void deleteByProjectId(Long projectId) {
    repository.deleteByProjectId(projectId);
  }

  @Transactional(rollbackOn = Exception.class)
  public void saveProjectMembers(Long projectId, Set<Long> userIds) {
    if (CollectionUtils.isEmpty(userIds)) {
      repository.deleteByProjectId(projectId);
      return;
    }
    List<ProjectUser> removed =
        repository.findByProjectId(projectId).stream()
            .filter(projectUser -> !userIds.remove(projectUser.getUserId()))
            .toList();
    if (CollectionUtils.isNotEmpty(removed)) {
      repository.deleteAll(removed);
    }
    if (CollectionUtils.isNotEmpty(userIds)) {
      List<ProjectUser> projectUsers = new ArrayList<>();
      for (Long userId : userIds) {
        ProjectUser projectUser = new ProjectUser();
        projectUser.setProjectId(projectId);
        projectUser.setUserId(userId);
        projectUsers.add(projectUser);
      }
      repository.saveAllAndFlush(projectUsers);
    }
  }
}

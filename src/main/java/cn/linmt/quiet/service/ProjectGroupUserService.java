package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.ProjectGroupUser;
import cn.linmt.quiet.repository.ProjectGroupUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectGroupUserService {

  private final ProjectGroupUserRepository repository;

  public void deleteByProjectGroupId(Long projectGroupId) {
    repository.deleteByProjectGroupId(projectGroupId);
  }

  public List<ProjectGroupUser> listByProjectGroupId(Long projectGroupId) {
    return repository.findByProjectGroupId(projectGroupId);
  }

  public void delete(List<ProjectGroupUser> removed) {
    if (CollectionUtils.isEmpty(removed)) {
      return;
    }
    repository.deleteAll(removed);
  }

  public void saveAll(List<ProjectGroupUser> users) {
    repository.saveAllAndFlush(users);
  }

  public List<ProjectGroupUser> listByUserId(Long userId) {
    return repository.findByUserId(userId);
  }
}

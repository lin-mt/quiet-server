package cn.linmt.quiet.manager;

import cn.linmt.quiet.entity.ProjectRepository;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.service.ProjectRepositoryService;
import cn.linmt.quiet.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepositoryManager {

  private final RepositoryService repositoryService;
  private final ProjectRepositoryService projectRepositoryService;

  public void deleteRepository(Long repositoryId) {
    List<ProjectRepository> projectRepositories =
        projectRepositoryService.listByRepositoryId(repositoryId);
    if (CollectionUtils.isNotEmpty(projectRepositories)) {
      throw new BizException(117000);
    }
    repositoryService.deleteById(repositoryId);
    projectRepositoryService.deleteByIds(
        projectRepositories.stream().map(ProjectRepository::getId).toList());
  }
}

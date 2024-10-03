package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.ProjectRepository;
import cn.linmt.quiet.repository.ProjectRepositoryRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectRepositoryService {

  private final ProjectRepositoryRepository projectRepositoryRepository;

  public List<ProjectRepository> listByRepositoryId(Long repositoryId) {
    return projectRepositoryRepository.findAllByRepositoryId(repositoryId);
  }

  public void deleteByIds(Collection<Long> ids) {
    projectRepositoryRepository.deleteAllById(ids);
  }

  public void saveAll(Long projectId, Set<Long> repositoryIds) {
    if (CollectionUtils.isEmpty(repositoryIds)) {
      projectRepositoryRepository.deleteAllByProjectId(projectId);
      return;
    }
    List<ProjectRepository> repositories =
        projectRepositoryRepository.findAllByProjectId(projectId);
    Set<Long> deleteRepositoryIds = new HashSet<>();
    for (ProjectRepository repository : repositories) {
      if (!repositoryIds.remove(repository.getRepositoryId())) {
        deleteRepositoryIds.add(repository.getRepositoryId());
      }
    }
    if (CollectionUtils.isNotEmpty(deleteRepositoryIds)) {
      projectRepositoryRepository.deleteAllById(deleteRepositoryIds);
    }
    if (CollectionUtils.isNotEmpty(repositoryIds)) {
      List<ProjectRepository> projectRepositories =
          repositoryIds.stream()
              .map(
                  id -> {
                    ProjectRepository projectRepository = new ProjectRepository();
                    projectRepository.setProjectId(projectId);
                    projectRepository.setRepositoryId(id);
                    return projectRepository;
                  })
              .toList();
      projectRepositoryRepository.saveAll(projectRepositories);
    }
  }

  public List<ProjectRepository> listByProjectId(Long projectId) {
    return projectRepositoryRepository.findAllByProjectId(projectId);
  }
}

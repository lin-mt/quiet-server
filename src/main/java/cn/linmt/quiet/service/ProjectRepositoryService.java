package cn.linmt.quiet.service;

import cn.linmt.quiet.controller.project.dto.ProjectRepositoryDTO;
import cn.linmt.quiet.entity.ProjectRepository;
import cn.linmt.quiet.repository.ProjectRepositoryRepository;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional(rollbackFor = Exception.class)
  public void saveAll(Long projectId, List<ProjectRepositoryDTO> repositories) {
    projectRepositoryRepository.deleteAllByProjectId(projectId);
    if (CollectionUtils.isEmpty(repositories)) {
      return;
    }
    List<ProjectRepository> projectRepositories =
        repositories.stream()
            .map(
                dto -> {
                  ProjectRepository repository = new ProjectRepository();
                  repository.setProjectId(projectId);
                  repository.setRepositoryId(dto.getRepositoryId());
                  repository.setAutoCreateBranch(dto.getAutoCreateBranch());
                  repository.setAutoCreatePullRequest(dto.getAutoCreatePullRequest());
                  return repository;
                })
            .toList();
    projectRepositoryRepository.saveAll(projectRepositories);
  }

  public List<ProjectRepository> listByProjectId(Long projectId) {
    return projectRepositoryRepository.findAllByProjectId(projectId);
  }

  public void deleteByProjectId(Long projectId) {
    projectRepositoryRepository.deleteAllByProjectId(projectId);
  }
}

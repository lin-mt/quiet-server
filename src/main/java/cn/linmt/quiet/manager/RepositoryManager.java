package cn.linmt.quiet.manager;

import cn.linmt.quiet.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryManager {

  private final RepositoryService repositoryService;

  public void deleteRepository(Long repositoryId) {
    repositoryService.deleteById(repositoryId);
  }
}

package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.ProjectRepository;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public interface ProjectRepositoryRepository extends QuietRepository<ProjectRepository> {

  List<ProjectRepository> findAllByRepositoryId(Long repositoryId);

  void deleteAllByProjectId(Long projectId);

  List<ProjectRepository> findAllByProjectId(Long projectId);
}

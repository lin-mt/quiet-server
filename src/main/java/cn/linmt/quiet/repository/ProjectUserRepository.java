package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.ProjectUser;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;

public interface ProjectUserRepository extends QuietRepository<ProjectUser> {
  List<ProjectUser> findByProjectId(Long projectId);

  void deleteByProjectId(Long projectId);
}

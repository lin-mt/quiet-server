package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.Project;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;

public interface ProjectRepository extends QuietRepository<Project> {
  Project findByNameIgnoreCase(String name);

  List<Project> findByProjectGroupId(Long projectGroupId);
}

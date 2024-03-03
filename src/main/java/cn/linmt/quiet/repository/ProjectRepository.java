package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.Project;
import cn.linmt.quiet.framework.QuietRepository;

public interface ProjectRepository extends QuietRepository<Project> {
  Project findByNameIgnoreCase(String name);
}

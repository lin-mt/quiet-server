package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.ProjectGroup;
import cn.linmt.quiet.framework.QuietRepository;

public interface ProjectGroupRepository extends QuietRepository<ProjectGroup> {
  ProjectGroup findByNameIgnoreCase(String name);
}

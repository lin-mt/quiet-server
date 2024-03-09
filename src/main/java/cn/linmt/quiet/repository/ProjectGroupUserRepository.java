package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.ProjectGroupUser;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;

public interface ProjectGroupUserRepository extends QuietRepository<ProjectGroupUser> {
  void deleteByProjectGroupId(Long projectGroupId);

  List<ProjectGroupUser> findByProjectGroupId(Long projectGroupId);

  List<ProjectGroupUser> findByUserId(Long userId);
}

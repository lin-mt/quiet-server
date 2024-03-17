package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.Version;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;

public interface VersionRepository extends QuietRepository<Version> {
  Version findByProjectIdAndNameIgnoreCase(Long projectId, String name);

  List<Version> findByProjectId(Long projectId);

  List<Version> findByParentId(Long parentId);
}

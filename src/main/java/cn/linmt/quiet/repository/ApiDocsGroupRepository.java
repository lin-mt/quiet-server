package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.ApiDocsGroup;
import cn.linmt.quiet.framework.QuietRepository;

public interface ApiDocsGroupRepository extends QuietRepository<ApiDocsGroup> {
  Long countByParentId(Long parentId);
}

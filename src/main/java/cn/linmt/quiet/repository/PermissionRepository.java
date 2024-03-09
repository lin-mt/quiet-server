package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.Permission;
import cn.linmt.quiet.enums.PermissionType;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends QuietRepository<Permission> {
  Optional<Permission> findByTypeAndValueIgnoreCase(PermissionType type, String value);

  List<Permission> findByParentId(Long parentId);
}

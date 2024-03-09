package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.RolePermission;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.Collection;
import java.util.List;

public interface RolePermissionRepository extends QuietRepository<RolePermission> {
  List<RolePermission> findByRoleIdIn(Collection<Long> roleIds);

  void deleteByPermissionId(Long permissionId);

  List<RolePermission> findByRoleId(Long roleId);
}

package cn.linmt.quiet.service;

import cn.linmt.quiet.action.DataInit;
import cn.linmt.quiet.entity.RolePermission;
import cn.linmt.quiet.repository.RolePermissionRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolePermissionService {
  private final RolePermissionRepository repository;

  public List<RolePermission> listByRoleIds(Collection<Long> roleIds) {
    return repository.findByRoleIdIn(roleIds);
  }

  public void deleteByPermissionId(Long id) {
    repository.deleteByPermissionId(id);
  }

  public void updatePermissions(Long roleId, Set<Long> permissionIds) {
    List<RolePermission> remove =
        repository.findByRoleId(roleId).stream()
            .filter(
                ur ->
                    !DataInit.RoleId.getAdmin().equals(ur.getRoleId())
                        && (CollectionUtils.isEmpty(permissionIds)
                            || !permissionIds.remove(ur.getRoleId())))
            .toList();
    if (CollectionUtils.isNotEmpty(remove)) {
      repository.deleteAll(remove);
    }
    if (CollectionUtils.isEmpty(permissionIds)) {
      return;
    }
    List<RolePermission> rolePermissions = new ArrayList<>();
    for (Long permissionId : permissionIds) {
      RolePermission rolePermission = new RolePermission();
      rolePermission.setRoleId(roleId);
      rolePermission.setPermissionId(permissionId);
      rolePermissions.add(rolePermission);
    }
    repository.saveAllAndFlush(rolePermissions);
  }

  public List<Long> listPermissionIdsByRoleId(Long roleId) {
    return repository.findByRoleId(roleId).stream().map(RolePermission::getPermissionId).toList();
  }
}

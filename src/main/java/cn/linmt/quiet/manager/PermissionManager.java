package cn.linmt.quiet.manager;

import cn.linmt.quiet.service.PermissionService;
import cn.linmt.quiet.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionManager {

  private final PermissionService permissionService;
  private final RolePermissionService rolePermissionService;

  public void delete(Long id) {
    permissionService.delete(id);
    rolePermissionService.deleteByPermissionId(id);
  }
}

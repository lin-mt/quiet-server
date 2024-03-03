package cn.linmt.quiet.manager;

import cn.linmt.quiet.service.RoleService;
import cn.linmt.quiet.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleManager {
  private final RoleService roleService;
  private final UserRoleService userRoleService;

  public void delete(Long id) {
    roleService.deleteById(id);
    userRoleService.deleteByRoleId(id);
  }
}

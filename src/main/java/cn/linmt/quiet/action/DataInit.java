package cn.linmt.quiet.action;

import cn.linmt.quiet.config.properties.ServerProperties;
import cn.linmt.quiet.entity.Role;
import cn.linmt.quiet.entity.User;
import cn.linmt.quiet.entity.UserRole;
import cn.linmt.quiet.enums.RoleCode;
import cn.linmt.quiet.service.RoleService;
import cn.linmt.quiet.service.UserRoleService;
import cn.linmt.quiet.service.UserService;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

  private final RoleService roleService;
  private final UserService userService;
  private final UserRoleService userRoleService;
  private final ServerProperties properties;

  @Override
  public void run(String... args) {
    // 初始化角色信息
    Map<String, RoleCode> code2Role = new HashMap<>();
    Map<String, String> code2parent = new HashMap<>();
    for (RoleCode roleCode : RoleCode.values()) {
      String code = roleCode.getCode();
      code2Role.put(code, roleCode);
      if (code.isEmpty() || code.length() == RoleCode.LEVEL_LENGTH) {
        continue;
      }
      String parentCode = code.substring(0, code.length() - RoleCode.LEVEL_LENGTH);
      code2parent.put(code, parentCode);
    }
    Map<String, Role> code2info =
        roleService.findByCodes(code2Role.keySet()).stream()
            .collect(Collectors.toMap(Role::getCode, r -> r));
    List<Role> newRoles = new ArrayList<>();
    for (Map.Entry<String, RoleCode> entry : code2Role.entrySet()) {
      Role role = code2info.get(entry.getKey());
      if (role == null) {
        Role newRole = new Role();
        RoleCode roleCode = entry.getValue();
        newRole.setCode(roleCode.getCode());
        newRole.setName(roleCode.getName());
        newRole.setValue(roleCode.getValue());
        newRoles.add(newRole);
      }
    }
    if (CollectionUtils.isNotEmpty(newRoles)) {
      List<Role> roles = roleService.saveAll(newRoles);
      for (Role role : roles) {
        code2info.put(role.getCode(), role);
      }
      for (Role role : roles) {
        String parentCode = code2parent.get(role.getCode());
        if (parentCode != null) {
          Role parentRole = code2info.get(parentCode);
          role.setParentId(parentRole.getId());
        }
      }
      roleService.saveAll(roles);
    }
    RoleId.ordinaryUser = code2info.get(RoleCode.OrdinaryUser.getCode()).getId();
    RoleId.admin = code2info.get(RoleCode.Admin.getCode()).getId();
    // 初始化管理员信息
    Optional<User> admin = userService.findByUsernameOption(properties.getAdminUsername());
    Long adminUserId;
    if (admin.isEmpty()) {
      User user = new User();
      user.setUsername(properties.getAdminUsername());
      user.setPassword(properties.getAdminPassword());
      adminUserId = userService.registration(user);
    } else {
      adminUserId = admin.get().getId();
    }
    List<UserRole> userRoles = userRoleService.listByUserId(adminUserId);
    boolean hasAdminRole = false;
    for (UserRole userRole : userRoles) {
      if (RoleId.admin.equals(userRole.getRoleId())) {
        hasAdminRole = true;
        break;
      }
    }
    if (!hasAdminRole) {
      UserRole userRole = new UserRole();
      userRole.setUserId(adminUserId);
      userRole.setRoleId(RoleId.admin);
      userRoleService.save(userRole);
    }
  }

  public static class RoleId {
    @Getter private static Long ordinaryUser = null;
    @Getter private static Long admin = null;
  }
}

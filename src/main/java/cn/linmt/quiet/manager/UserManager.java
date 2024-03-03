package cn.linmt.quiet.manager;

import cn.linmt.quiet.entity.Role;
import cn.linmt.quiet.entity.User;
import cn.linmt.quiet.entity.UserRole;
import cn.linmt.quiet.modal.app.RoleInfo;
import cn.linmt.quiet.modal.app.UserInfo;
import cn.linmt.quiet.service.RoleService;
import cn.linmt.quiet.service.UserRoleService;
import cn.linmt.quiet.service.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManager implements UserDetailsService {
  private final UserService userService;
  private final UserRoleService userRoleService;
  private final RoleService roleService;
  private final GrantedAuthorityDefaults grantedAuthorityDefaults;

  @Override
  @Cacheable(value = "userDetailsCache", key = "#username")
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username);
    List<Long> roleIds =
        userRoleService.listByUserId(user.getId()).stream().map(UserRole::getRoleId).toList();
    List<Role> roles = roleService.listByIdContainsChild(roleIds);
    List<RoleInfo> userRoles = new ArrayList<>();
    for (Role role : roles) {
      RoleInfo roleInfo = new RoleInfo();
      BeanUtils.copyProperties(role, roleInfo);
      roleInfo.setValue(grantedAuthorityDefaults.getRolePrefix() + role.getValue());
      userRoles.add(roleInfo);
    }
    UserInfo userInfo = new UserInfo();
    BeanUtils.copyProperties(user, userInfo);
    userInfo.setRoles(userRoles);
    return userInfo;
  }
}

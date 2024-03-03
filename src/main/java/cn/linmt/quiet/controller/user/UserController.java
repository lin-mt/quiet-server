package cn.linmt.quiet.controller.user;

import cn.linmt.quiet.action.DataInit.RoleId;
import cn.linmt.quiet.controller.role.vo.RoleInfo;
import cn.linmt.quiet.controller.user.dto.PageUser;
import cn.linmt.quiet.controller.user.dto.UpdateUser;
import cn.linmt.quiet.controller.user.dto.UserDTO;
import cn.linmt.quiet.controller.user.dto.UserRoles;
import cn.linmt.quiet.controller.user.vo.CurrentUser;
import cn.linmt.quiet.controller.user.vo.SimpleUser;
import cn.linmt.quiet.controller.user.vo.UserPermission;
import cn.linmt.quiet.controller.user.vo.UserVO;
import cn.linmt.quiet.entity.Permission;
import cn.linmt.quiet.entity.RolePermission;
import cn.linmt.quiet.entity.User;
import cn.linmt.quiet.modal.app.UserInfo;
import cn.linmt.quiet.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final JwtService jwtService;
  private final UserRoleService userRoleService;
  private final AuthenticationManager authenticationManager;
  private final RolePermissionService rolePermissionService;
  private final PermissionService permissionService;

  @Operation(summary = "查询用户拥有的角色ID")
  @GetMapping("/listRoles")
  public Set<Long> listRoles(@RequestParam @Schema(description = "用户ID") Long userId) {
    return userRoleService.listRoleIdsByUserId(userId);
  }

  @Operation(summary = "根据用户名查询用户（10条数据）")
  @GetMapping("/listUser")
  public List<SimpleUser> listUser(@RequestParam @Schema(description = "用户名") String username) {
    return userService.listUser(username, 10L);
  }

  @Operation(summary = "更新用户角色信息")
  @PutMapping("/updateRoles")
  public void updateRoles(@RequestBody UserRoles userRoles) {
    userRoleService.updateRoles(userRoles.getUserId(), userRoles.getRoleIds());
  }

  @Operation(summary = "分页查询用户")
  @GetMapping("/page")
  public Page<UserVO> pageUser(@NotNull PageUser pageUser) {
    Page<User> page = userService.page(pageUser);
    return page.map(
        user -> {
          UserVO userVO = new UserVO();
          BeanUtils.copyProperties(user, userVO);
          return userVO;
        });
  }

  @Operation(summary = "注册用户")
  @PostMapping("/registration")
  public Long registration(@RequestBody UserDTO userDTO) {
    User user = new User();
    user.setUsername(userDTO.getUsername());
    user.setPassword(userDTO.getPassword());
    return userService.registration(user);
  }

  @PutMapping
  @Operation(summary = "更新用户")
  public Long updateUser(@Validated @RequestBody UpdateUser user) {
    User newUser = new User();
    BeanUtils.copyProperties(user, newUser);
    return userService.update(newUser);
  }

  @DeleteMapping
  @Operation(summary = "删除用户")
  public void deleteUser(@Schema(description = "用户ID") Long id) {
    userService.delete(id);
  }

  @Operation(summary = "用户登陆")
  @PostMapping("/login")
  public String login(@Validated @RequestBody UserDTO authRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()));
    Object principal = authentication.getPrincipal();
    if (authentication.isAuthenticated() && principal instanceof UserInfo userInfo) {
      return jwtService.generateToken(userInfo.getUsername(), userInfo.getId());
    }
    throw new RuntimeException("用户不存在或密码错误");
  }

  @Operation(summary = "获取当前用户信息")
  @GetMapping("/current")
  @Cacheable(value = "currentUserCache", key = "#authentication.principal.id")
  public CurrentUser currentUser(Authentication authentication) {
    Object principal = authentication.getPrincipal();
    CurrentUser user = new CurrentUser();
    if (principal instanceof UserInfo userInfo) {
      user.setId(userInfo.getId());
      user.setUsername(userInfo.getUsername());
      Set<Long> roleIds = new HashSet<>();
      List<RoleInfo> roleInfos =
          userInfo.getRoles().stream()
              .map(
                  role -> {
                    RoleInfo roleInfo = new RoleInfo();
                    roleIds.add(role.getId());
                    roleInfo.setId(role.getId());
                    roleInfo.setName(role.getName());
                    roleInfo.setValue(role.getValue());
                    return roleInfo;
                  })
              .toList();
      user.setRoles(roleInfos);
      user.setPermission(getPermission(roleIds));
      return user;
    }
    throw new IllegalStateException();
  }

  private UserPermission getPermission(Set<Long> roleIds) {
    UserPermission userPermission = new UserPermission();
    if (CollectionUtils.isEmpty(roleIds)) {
      roleIds = new HashSet<>();
    }
    roleIds.add(RoleId.getOrdinaryUser());
    boolean isAdmin = roleIds.contains(RoleId.getAdmin());
    List<Long> permissionIds =
        rolePermissionService.listByRoleIds(roleIds).stream()
            .map(RolePermission::getPermissionId)
            .toList();
    List<Permission> allPermission = permissionService.findAll();
    Set<String> paths = new HashSet<>();
    for (Permission permission : allPermission) {
      if (isAdmin) {
        paths.add(permission.getPath());
        continue;
      }
      if (permissionIds.contains(permission.getId())) {
        paths.add(permission.getPath());
      }
    }
    userPermission.setPaths(paths);
    return userPermission;
  }
}

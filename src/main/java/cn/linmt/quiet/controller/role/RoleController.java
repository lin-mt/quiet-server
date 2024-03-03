package cn.linmt.quiet.controller.role;

import cn.linmt.quiet.action.DataInit.RoleId;
import cn.linmt.quiet.controller.role.dto.AddRole;
import cn.linmt.quiet.controller.role.dto.PageRole;
import cn.linmt.quiet.controller.role.dto.RolePermissions;
import cn.linmt.quiet.controller.role.dto.UpdateRole;
import cn.linmt.quiet.controller.role.vo.RoleVO;
import cn.linmt.quiet.controller.role.vo.TreeRole;
import cn.linmt.quiet.entity.Role;
import cn.linmt.quiet.enums.RoleCode;
import cn.linmt.quiet.manager.RoleManager;
import cn.linmt.quiet.service.RolePermissionService;
import cn.linmt.quiet.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

  private final RoleService roleService;
  private final RoleManager roleManager;
  private final RolePermissionService rolePermissionService;

  @GetMapping("/listPermission")
  @Operation(summary = "查询角色拥有的权限ID")
  public List<Long> listPermission(@RequestParam @Schema(description = "角色ID") Long roleId) {
    return rolePermissionService.listPermissionIdsByRoleId(roleId);
  }

  @Operation(summary = "更新角色权限信息")
  @PutMapping("/updatePermissions")
  public void updatePermissions(@RequestBody RolePermissions rolePermissions) {
    rolePermissionService.updatePermissions(
        rolePermissions.getRoleId(), rolePermissions.getPermissionIds());
  }

  @GetMapping("/page")
  @Operation(summary = "分页查询角色信息")
  public Page<RoleVO> pageRole(@NotNull PageRole pageRole) {
    Page<Role> roles = roleService.page(pageRole);
    return roles.map(
        role -> {
          RoleVO roleVO = new RoleVO();
          BeanUtils.copyProperties(role, roleVO);
          String code = role.getCode();
          if (!role.getCode().isEmpty()) {
            roleVO.setParentCode(code.substring(0, code.length() - RoleCode.LEVEL_LENGTH));
          }
          return roleVO;
        });
  }

  @GetMapping("/treeSelect")
  @Operation(summary = "查询所有角色信息，返回树形结构")
  public List<TreeRole> treeRoles() {
    List<Role> roles = roleService.findAll();
    roles.sort(
        Comparator.<Role>comparingInt(r -> r.getCode().length()).thenComparing(Role::getOrdinal));
    List<TreeRole> roleVOS = new ArrayList<>();
    Map<Long, TreeRole> id2tree = new HashMap<>();
    for (Role role : roles) {
      TreeRole treeRole = new TreeRole();
      BeanUtils.copyProperties(role, treeRole);
      id2tree.put(role.getId(), treeRole);
      if (!RoleId.getAdmin().equals(role.getId()) && role.getParentId() == null) {
        roleVOS.add(treeRole);
      }
    }
    id2tree.forEach(
        (id, treeRole) -> {
          TreeRole parent = id2tree.get(treeRole.getParentId());
          if (parent != null) {
            List<TreeRole> children = parent.getChildren();
            if (children == null) {
              parent.setChildren(new ArrayList<>());
            }
            parent.getChildren().add(treeRole);
          }
        });
    return roleVOS;
  }

  @PostMapping
  @Operation(summary = "新增角色")
  public Long addRole(@RequestBody @Validated AddRole addRole) {
    Role role = new Role();
    BeanUtils.copyProperties(addRole, role);
    return roleService.save(role);
  }

  @PutMapping
  @Operation(summary = "更新角色")
  public Long updateRole(@RequestBody @Validated UpdateRole updateRole) {
    Role role = new Role();
    BeanUtils.copyProperties(updateRole, role);
    return roleService.save(role);
  }

  @DeleteMapping
  @Operation(summary = "删除角色")
  public void deleteRole(@Schema(description = "角色ID") Long id) {
    roleManager.delete(id);
  }
}

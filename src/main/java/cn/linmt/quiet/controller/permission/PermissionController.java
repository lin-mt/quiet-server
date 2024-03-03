package cn.linmt.quiet.controller.permission;

import cn.linmt.quiet.controller.permission.dto.AddPermission;
import cn.linmt.quiet.controller.permission.dto.PagePermission;
import cn.linmt.quiet.controller.permission.dto.UpdatePermission;
import cn.linmt.quiet.controller.permission.vo.PermissionVO;
import cn.linmt.quiet.controller.permission.vo.TreePermission;
import cn.linmt.quiet.entity.Permission;
import cn.linmt.quiet.manager.PermissionManager;
import cn.linmt.quiet.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permission")
public class PermissionController {

  private final PermissionService permissionService;
  private final PermissionManager permissionManager;

  @GetMapping("/page")
  @PreAuthorize("hasRole('Administrator')")
  @Operation(summary = "分页查询权限信息")
  public Page<PermissionVO> pagePermission(@NotNull PagePermission pagePermission) {
    Page<Permission> permissions = permissionService.page(pagePermission);
    return permissions.map(
        permission -> {
          PermissionVO permissionVO = new PermissionVO();
          BeanUtils.copyProperties(permission, permissionVO);
          return permissionVO;
        });
  }

  @GetMapping("/treeSelect")
  @Operation(summary = "查询所有权限信息，返回树形结构")
  public List<TreePermission> treePermission() {
    List<Permission> permissions = permissionService.findAll();
    List<TreePermission> treePermissions = new ArrayList<>();
    Map<Long, TreePermission> id2tree = new LinkedHashMap<>();
    for (Permission permission : permissions) {
      TreePermission treePermission = new TreePermission();
      BeanUtils.copyProperties(permission, treePermission);
      id2tree.put(permission.getId(), treePermission);
    }
    id2tree.forEach(
        (id, treePermission) -> {
          TreePermission parent = id2tree.get(treePermission.getParentId());
          if (parent != null) {
            List<TreePermission> children = parent.getChildren();
            if (children == null) {
              parent.setChildren(new ArrayList<>());
            }
            parent.getChildren().add(treePermission);
          } else {
            treePermissions.add(treePermission);
          }
        });
    return treePermissions;
  }

  @PostMapping
  @Operation(summary = "新增权限信息")
  public Long addPermission(@RequestBody @Validated AddPermission addPermission) {
    Permission permission = new Permission();
    BeanUtils.copyProperties(addPermission, permission);
    return permissionService.save(permission).getId();
  }

  @PutMapping
  @Operation(summary = "更新权限信息")
  public Long updatePermission(@RequestBody @Validated UpdatePermission updatePermission) {
    Permission permission = new Permission();
    BeanUtils.copyProperties(updatePermission, permission);
    return permissionService.save(permission).getId();
  }

  @DeleteMapping
  @Operation(summary = "删除权限")
  public void deletePermission(@Schema(description = "权限ID") Long id) {
    permissionManager.delete(id);
  }
}

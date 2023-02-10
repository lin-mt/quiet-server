/*
 * Copyright (C) 2023 lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.quiet.controller.system;

import com.github.quiet.convert.system.QuietRoleConvert;
import com.github.quiet.dto.system.QuietRoleDTO;
import com.github.quiet.entity.system.QuietRole;
import com.github.quiet.manager.system.QuietRoleManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.system.QuietRoleService;
import com.github.quiet.utils.EntityUtils;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.system.QuietRoleVO;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class QuietRoleController {

  private final QuietRoleService roleService;
  private final QuietRoleManager roleManager;
  private final QuietRoleConvert roleConvert;

  /**
   * 以树形结构查询角色之间的关联信息.
   *
   * @return 角色之间的关联关系
   */
  @GetMapping("/tree")
  public Result<List<QuietRoleVO>> tree() {
    List<QuietRole> treeRoles = roleService.findAll();
    return Result.success(EntityUtils.buildTreeData(roleConvert.entities2vos(treeRoles)));
  }

  /**
   * 分页查询角色.
   *
   * @return 查询所有信息
   */
  @GetMapping("/page")
  public Result<Page<QuietRoleVO>> page(QuietRoleDTO dto) {
    Page<QuietRole> rolePage = roleService.page(roleConvert.dto2entity(dto), dto.page());
    Page<QuietRoleVO> roles = roleConvert.page2page(rolePage);
    if (!roles.isEmpty()) {
      Set<Long> parentIds =
          roles.getContent().stream()
              .map(QuietRoleVO::getParentId)
              .filter(Objects::nonNull)
              .collect(Collectors.toSet());
      if (CollectionUtils.isNotEmpty(parentIds)) {
        Map<Long, QuietRole> idToRoleInfo =
            roleService.findAllByIds(parentIds).stream()
                .collect(Collectors.toMap(QuietRole::getId, role -> role));
        for (QuietRoleVO role : roles.getContent()) {
          if (role.getParentId() != null) {
            role.setParentRoleName(idToRoleInfo.get(role.getParentId()).getRoleName());
          }
        }
      }
    }
    return Result.success(roles);
  }

  /**
   * 新增角色.
   *
   * @param dto 新增的角色信息
   * @return 新增后的角色信息
   */
  @PostMapping
  public Result<QuietRoleVO> save(@RequestBody @Validated(Create.class) QuietRoleDTO dto) {
    QuietRole save = roleService.save(roleConvert.dto2entity(dto));
    return Result.createSuccess(roleConvert.entity2vo(save));
  }

  /**
   * 删除角色.
   *
   * @param id 删除的角色ID
   * @return Result
   */
  @DeleteMapping("/{id}")
  @PreAuthorize(value = "hasRole('Admin')")
  public Result<Object> delete(@PathVariable Long id) {
    roleManager.deleteRole(id);
    return Result.deleteSuccess();
  }

  /**
   * 更新角色.
   *
   * @param dto 更新的角色信息
   * @return 新增后的角色信息
   */
  @PutMapping
  public Result<QuietRoleVO> update(@RequestBody @Validated(Update.class) QuietRoleDTO dto) {
    QuietRole update = roleService.update(roleConvert.dto2entity(dto));
    return Result.updateSuccess(roleConvert.entity2vo(update));
  }
}

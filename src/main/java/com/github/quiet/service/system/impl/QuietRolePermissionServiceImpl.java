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

package com.github.quiet.service.system.impl;

import com.github.quiet.entity.system.QuietRolePermission;
import com.github.quiet.repository.system.QuietRolePermissionRepository;
import com.github.quiet.service.system.QuietRolePermissionService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * 角色-权限 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietRolePermissionServiceImpl implements QuietRolePermissionService {

  private final QuietRolePermissionRepository rolePermissionRepository;

  public QuietRolePermissionServiceImpl(QuietRolePermissionRepository rolePermissionRepository) {
    this.rolePermissionRepository = rolePermissionRepository;
  }

  @Override
  public QuietRolePermission saveOrUpdate(@NotNull QuietRolePermission rolePermission) {
    QuietRolePermission exist =
        rolePermissionRepository.getByRoleIdAndPermissionId(
            rolePermission.getRoleId(), rolePermission.getPermissionId());
    if (exist != null) {
      rolePermission.setId(exist.getId());
    }
    return rolePermissionRepository.save(rolePermission);
  }

  @Override
  public void delete(@NotNull Long deleteId) {
    rolePermissionRepository.deleteById(deleteId);
  }
}

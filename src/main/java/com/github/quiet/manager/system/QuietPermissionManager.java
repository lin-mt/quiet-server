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

package com.github.quiet.manager.system;

import com.github.quiet.entity.system.QuietPermission;
import com.github.quiet.exception.ServiceException;
import com.github.quiet.repository.system.QuietPermissionRepository;
import com.github.quiet.service.system.QuietPermissionService;
import com.github.quiet.service.system.QuietRoleService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietPermissionManager {

  private final QuietPermissionRepository permissionRepository;
  private final QuietRoleService roleService;

  /**
   * 新增或更新权限信息.
   *
   * @param permission 新增或更新的权限信息
   * @return 新增或更新的权限信息
   */
  @CacheEvict(value = QuietPermissionService.CACHE_INFO, key = "#permission.applicationName")
  public QuietPermission saveOrUpdate(@NotNull QuietPermission permission) {
    if (!roleService.existsById(permission.getRoleId())) {
      throw new ServiceException("role.id.not.exist", permission.getRoleId());
    }
    return permissionRepository.saveAndFlush(permission);
  }
}

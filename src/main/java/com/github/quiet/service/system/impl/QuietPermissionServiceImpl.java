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

import com.github.quiet.entity.system.QuietPermission;
import com.github.quiet.repository.system.QuietPermissionRepository;
import com.github.quiet.service.system.QuietPermissionService;
import com.github.quiet.utils.SelectBuilder;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限 Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietPermissionServiceImpl implements QuietPermissionService {

  private final QuietPermissionRepository permissionRepository;

  @Override
  @CacheEvict(value = CACHE_INFO, key = "#result.applicationName")
  public QuietPermission delete(@NotNull Long deleteId) {
    QuietPermission deleted = permissionRepository.getReferenceById(deleteId);
    permissionRepository.deleteById(deleteId);
    return deleted;
  }

  @Override
  public Page<QuietPermission> page(QuietPermission params, @NotNull Pageable page) {
    BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
    return permissionRepository.findAll(predicate, page);
  }

  @Override
  public List<QuietPermission> listByRoleId(@NotNull Long roleId) {
    return permissionRepository.findAllByRoleId(roleId);
  }
}

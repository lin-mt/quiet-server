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

import com.github.quiet.entity.system.QuietRole;
import com.github.quiet.entity.system.QuietUserRole;
import com.github.quiet.exception.ServiceException;
import com.github.quiet.repository.system.QuietUserRoleRepository;
import com.github.quiet.service.system.QuietRoleService;
import com.github.quiet.service.system.QuietUserRoleService;
import com.github.quiet.service.system.QuietUserService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietUserRoleManager {

  private final QuietUserRoleService userRoleService;
  private final QuietUserRoleRepository userRoleRepository;
  private final QuietRoleService roleService;
  private final QuietUserService userService;

  /**
   * 用户新增或更新角色信息.
   *
   * @param userRole 用户-角色信息
   * @return 用户-角色关联信息
   */
  public QuietUserRole saveOrUpdate(@NotNull QuietUserRole userRole) {
    if (!userService.existsById(userRole.getUserId())) {
      throw new ServiceException("userRole.user.id.no.exist", userRole.getUserId());
    }
    if (!roleService.existsById(userRole.getRoleId())) {
      throw new ServiceException("userRole.role.id.no.exist", userRole.getRoleId());
    }
    Optional<QuietUserRole> exist =
        userRoleRepository.findByUserIdAndRoleId(userRole.getUserId(), userRole.getRoleId());
    exist.ifPresent(ur -> userRole.setId(ur.getId()));
    return userRoleRepository.save(userRole);
  }

  /**
   * 批量查询用户拥有的角色
   *
   * @param userIds 要查询的用户信息
   * @return 用户ID与角色信息的对应集合
   */
  public Map<Long, List<QuietRole>> mapUserIdToRoleInfo(Collection<Long> userIds) {
    if (CollectionUtils.isNotEmpty(userIds)) {
      List<QuietUserRole> allUserRoles = userRoleService.findRolesByUserIds(userIds);
      if (CollectionUtils.isNotEmpty(allUserRoles)) {
        Map<Long, List<QuietUserRole>> userIdToUserRoles =
            allUserRoles.stream().collect(Collectors.groupingBy(QuietUserRole::getUserId));
        Set<Long> roleIds =
            allUserRoles.stream().map(QuietUserRole::getRoleId).collect(Collectors.toSet());
        Map<Long, QuietRole> roleIdToRoleInfo =
            roleService.findAllByIds(roleIds).stream()
                .collect(Collectors.toMap(QuietRole::getId, val -> val));
        Map<Long, List<QuietRole>> result = new HashMap<>(userIds.size());
        for (Long userId : userIds) {
          List<QuietUserRole> userRoles = userIdToUserRoles.get(userId);
          result.put(userId, new ArrayList<>());
          if (CollectionUtils.isNotEmpty(userRoles)) {
            for (QuietUserRole userRole : userRoles) {
              result.get(userId).add(roleIdToRoleInfo.get(userRole.getRoleId()));
            }
          }
        }
        return result;
      }
    }
    return Collections.emptyMap();
  }
}

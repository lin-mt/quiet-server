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

import com.github.quiet.entity.system.QuietTeamUserRole;
import com.github.quiet.repository.system.QuietTeamUserRoleRepository;
import com.github.quiet.service.system.QuietTeamUserRoleService;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 用户团队角色Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietTeamUserRoleServiceImpl implements QuietTeamUserRoleService {

  private final QuietTeamUserRoleRepository teamUserRoleRepository;

  @Override
  public List<QuietTeamUserRole> findByTeamUserIds(Set<Long> teamUserIds) {
    return teamUserRoleRepository.findByTeamUserIdIsIn(teamUserIds);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByTeamUserIds(@NotNull @NotEmpty Set<Long> teamUserIds) {
    teamUserRoleRepository.removeAllByTeamUserIdIsIn(teamUserIds);
  }

  @Override
  public void saveAll(List<QuietTeamUserRole> teamUserRoles) {
    teamUserRoleRepository.saveAll(teamUserRoles);
  }
}

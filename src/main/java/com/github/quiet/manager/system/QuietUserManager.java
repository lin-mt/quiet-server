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
import com.github.quiet.entity.system.QuietTeamUser;
import com.github.quiet.entity.system.QuietUser;
import com.github.quiet.entity.system.QuietUserRole;
import com.github.quiet.repository.system.QuietUserRepository;
import com.github.quiet.service.system.QuietDeptUserService;
import com.github.quiet.service.system.QuietRoleService;
import com.github.quiet.service.system.QuietTeamUserRoleService;
import com.github.quiet.service.system.QuietTeamUserService;
import com.github.quiet.service.system.QuietUserRoleService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietUserManager implements UserDetailsService {

  private final QuietUserRepository userRepository;
  private final QuietUserRoleService userRoleService;
  private final QuietRoleService roleService;
  private final QuietDeptUserService deptUserService;
  private final QuietTeamUserService teamUserService;
  private final QuietTeamUserRoleService teamUserRoleService;

  @Override
  public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
    QuietUser user = userRepository.getByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("???????????????");
    }
    List<QuietUserRole> quietUserRoles = userRoleService.findByUserId(user.getId());
    if (CollectionUtils.isNotEmpty(quietUserRoles)) {
      Set<Long> roleIds =
          quietUserRoles.stream().map(QuietUserRole::getRoleId).collect(Collectors.toSet());
      List<QuietRole> roles =
          roleService.getReachableGrantedAuthorities(roleService.findAllByIds(roleIds));
      user.setAuthorities(roles);
    }
    return user;
  }

  /**
   * ????????????.
   *
   * @param userId ?????????????????????ID
   * @return true???????????????
   */
  public boolean delete(@NotNull Long userId) {
    // ????????????-????????????
    userRoleService.deleteByUserId(userId);
    // ????????????-????????????
    deptUserService.deleteByUserId(userId);
    // ????????????-????????????
    List<QuietTeamUser> allTeamUser = teamUserService.findAllByUserId(userId);
    if (CollectionUtils.isNotEmpty(allTeamUser)) {
      teamUserRoleService.deleteByTeamUserIds(
          allTeamUser.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
    }
    teamUserService.deleteByUserId(userId);
    // TODO ????????????????????????????????????
    // ??????????????????
    userRepository.deleteById(userId);
    return true;
  }
}

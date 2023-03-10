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

import com.github.quiet.constant.service.RoleNames;
import com.github.quiet.entity.system.QuietRole;
import com.github.quiet.entity.system.QuietTeam;
import com.github.quiet.entity.system.QuietTeamUser;
import com.github.quiet.entity.system.QuietTeamUserRole;
import com.github.quiet.entity.system.QuietUser;
import com.github.quiet.exception.ServiceException;
import com.github.quiet.repository.system.QuietTeamRepository;
import com.github.quiet.service.system.QuietRoleService;
import com.github.quiet.service.system.QuietTeamUserRoleService;
import com.github.quiet.service.system.QuietTeamUserService;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietTeamManager {

  private final QuietTeamRepository teamRepository;
  private final QuietTeamUserService teamUserService;
  private final QuietTeamUserRoleService teamUserRoleService;
  private final QuietRoleService roleService;

  /**
   * ???????????????????????????
   *
   * @param team ????????????
   * @param productOwners PO
   * @param scrumMasters SM
   * @param members ????????????
   * @return ?????????????????????????????????
   */
  public QuietTeam saveOrUpdate(
      QuietTeam team,
      List<QuietUser> productOwners,
      List<QuietUser> scrumMasters,
      List<QuietUser> members) {
    QuietTeam exist = teamRepository.getByTeamName(team.getTeamName());
    if (exist != null && !exist.getId().equals(team.getId())) {
      throw new ServiceException("team.teamName.exist", team.getTeamName());
    }
    // ??????????????????
    QuietTeam quietTeam = teamRepository.saveAndFlush(team);
    // ??????????????????????????????????????????????????????????????????????????????
    List<QuietTeamUser> allUsers = teamUserService.findAllByTeamId(quietTeam.getId());
    if (CollectionUtils.isNotEmpty(allUsers)) {
      teamUserRoleService.deleteByTeamUserIds(
          allUsers.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
      teamUserService.deleteByTeamId(quietTeam.getId());
    }
    Set<Long> memberIds = new HashSet<>();
    // ??????????????????????????? PO ??? SM
    this.addMemberId(memberIds, members);
    this.addMemberId(memberIds, productOwners);
    this.addMemberId(memberIds, scrumMasters);
    // ????????????????????????
    teamUserService.addUsers(quietTeam.getId(), memberIds);
    // ?????? PO ??????
    if (CollectionUtils.isNotEmpty(productOwners)) {
      this.addRoleForTeam(
          quietTeam.getId(),
          productOwners.stream().map(QuietUser::getId).collect(Collectors.toSet()),
          RoleNames.ProductOwner);
    }
    // ?????? SM ??????
    if (CollectionUtils.isNotEmpty(scrumMasters)) {
      this.addRoleForTeam(
          quietTeam.getId(),
          scrumMasters.stream().map(QuietUser::getId).collect(Collectors.toSet()),
          RoleNames.ScrumMaster);
    }
    return quietTeam;
  }

  /**
   * ?????????????????????????????????????????????????????????????????????????????????
   *
   * @param teamId ??????ID
   * @param userIds ????????????????????????ID??????
   * @param roleName ????????????
   */
  public void addRoleForTeam(
      @NotNull Long teamId, @NotEmpty Set<Long> userIds, @NotNull String roleName) {
    List<QuietTeamUser> teamUsers = teamUserService.findByTeamIdAndUserIds(teamId, userIds);
    if (CollectionUtils.isEmpty(teamUsers)) {
      // ?????????????????????ID??????????????????
      return;
    }
    Map<String, QuietTeamUserRole> teamUserIdToRole =
        teamUserRoleService
            .findByTeamUserIds(
                teamUsers.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()))
            .stream()
            .collect(Collectors.toMap(this::buildTeamUserIdToRole, v -> v));
    QuietRole role = roleService.findByRoleName(roleName);
    List<QuietTeamUserRole> newRoles = new ArrayList<>(teamUsers.size());
    for (QuietTeamUser teamUser : teamUsers) {
      if (MapUtils.isNotEmpty(teamUserIdToRole)) {
        QuietTeamUserRole exist = teamUserIdToRole.get(buildTeamUserIdToRole(teamUser, role));
        if (exist != null && exist.getRoleId().equals(role.getId())) {
          continue;
        }
      }
      newRoles.add(new QuietTeamUserRole(teamUser.getId(), role.getId()));
    }
    if (CollectionUtils.isNotEmpty(newRoles)) {
      teamUserRoleService.saveAll(newRoles);
    }
  }

  private String buildTeamUserIdToRole(@NotNull QuietTeamUserRole teamUserRole) {
    return teamUserRole.getTeamUserId().toString() + "-" + teamUserRole.getRoleId().toString();
  }

  private String buildTeamUserIdToRole(@NotNull QuietTeamUser teamUser, @NotNull QuietRole role) {
    return teamUser.getId().toString() + "-" + role.getId().toString();
  }

  private void addMemberId(@NotNull Set<Long> memberIds, List<QuietUser> members) {
    if (CollectionUtils.isNotEmpty(members)) {
      for (QuietUser member : members) {
        memberIds.add(member.getId());
      }
    }
  }

  public void deleteTeam(@NotNull Long deleteId) {
    // ????????????????????????
    teamUserService.deleteByTeamId(deleteId);
    // ??????????????????
    teamRepository.deleteById(deleteId);
  }
}

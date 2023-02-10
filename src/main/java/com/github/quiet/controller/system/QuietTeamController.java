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

import com.github.quiet.constant.service.RoleNames;
import com.github.quiet.convert.system.QuietTeamConvert;
import com.github.quiet.convert.system.QuietUserConvert;
import com.github.quiet.dto.system.QuietTeamDTO;
import com.github.quiet.entity.system.QuietRole;
import com.github.quiet.entity.system.QuietTeam;
import com.github.quiet.entity.system.QuietTeamUser;
import com.github.quiet.entity.system.QuietTeamUserRole;
import com.github.quiet.entity.system.QuietUser;
import com.github.quiet.manager.system.QuietTeamManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.system.QuietRoleService;
import com.github.quiet.service.system.QuietTeamService;
import com.github.quiet.service.system.QuietTeamUserRoleService;
import com.github.quiet.service.system.QuietTeamUserService;
import com.github.quiet.service.system.QuietUserService;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.system.QuietTeamVO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 团队 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/team")
public class QuietTeamController {

  private final QuietTeamService teamService;
  private final QuietTeamManager teamManager;
  private final QuietTeamConvert teamConvert;
  private final QuietTeamUserService teamUserService;
  private final QuietTeamUserRoleService teamUserRoleService;
  private final QuietRoleService roleService;
  private final QuietUserService userService;
  private final QuietUserConvert userConvert;

  /**
   * 根据团队ID获取团队信息
   *
   * @param id 团队ID
   * @return 团队信息
   */
  @GetMapping("/{id}")
  public Result<QuietTeamVO> get(@PathVariable Long id) {
    QuietTeam team = teamService.findById(id);
    List<QuietTeamUser> teamUsers = teamUserService.findByTeamId(id);
    Set<Long> userIds =
        teamUsers.stream().map(QuietTeamUser::getUserId).collect(Collectors.toSet());
    List<QuietTeamUserRole> userTeamRoles =
        teamUserRoleService.findByTeamUserIds(
            teamUsers.stream().map(QuietTeamUser::getId).collect(Collectors.toSet()));
    Map<Long, List<QuietTeamUserRole>> teamUserIdToRoles =
        userTeamRoles.stream().collect(Collectors.groupingBy(QuietTeamUserRole::getTeamUserId));
    Map<Long, QuietUser> userIdToUserInfo =
        userService.findByUserIds(userIds).stream()
            .collect(Collectors.toMap(QuietUser::getId, u -> u));
    QuietRole productOwner = roleService.findByRoleName(RoleNames.ProductOwner);
    QuietRole scrumMaster = roleService.findByRoleName(RoleNames.ScrumMaster);
    List<QuietUser> members = new ArrayList<>();
    List<QuietUser> teamProductOwners = new ArrayList<>();
    List<QuietUser> teamScrumMasters = new ArrayList<>();
    for (QuietTeamUser teamUser : teamUsers) {
      List<QuietTeamUserRole> teamUserRoles = teamUserIdToRoles.get(teamUser.getId());
      if (CollectionUtils.isNotEmpty(teamUserRoles)) {
        for (QuietTeamUserRole quietTeamUserRole : teamUserRoles) {
          if (quietTeamUserRole.getRoleId().equals(productOwner.getId())) {
            teamProductOwners.add(userIdToUserInfo.get(teamUser.getUserId()));
          }
          if (quietTeamUserRole.getRoleId().equals(scrumMaster.getId())) {
            teamScrumMasters.add(userIdToUserInfo.get(teamUser.getUserId()));
          }
        }
      }
      members.add(userIdToUserInfo.get(teamUser.getUserId()));
    }
    QuietTeamVO vo = teamConvert.entity2vo(team);
    vo.setProductOwners(userConvert.entities2vos(teamProductOwners));
    vo.setScrumMasters(userConvert.entities2vos(teamScrumMasters));
    vo.setMembers(userConvert.entities2vos(members));
    return Result.success(vo);
  }

  /**
   * 根据团队ID、成员ID、团队名称查询团队信息
   *
   * @param dto :teamName 团队名称 :id 团队ID :userId 团队成员ID
   * @return 团队信息
   */
  @GetMapping("/list-teams")
  public Result<List<QuietTeamVO>> listTeams(QuietTeamDTO dto) {
    List<QuietTeam> teams =
        teamService.listTeams(dto.getId(), dto.getTeamUserId(), dto.getTeamName(), dto.getIds());
    return Result.success(teamConvert.entities2vos(teams));
  }

  /**
   * 分页查询团队信息.
   *
   * @param dto 查询参数
   * @return 团队信息
   */
  @GetMapping("/page")
  public Result<Page<QuietTeamVO>> page(QuietTeamDTO dto) {
    Page<QuietTeam> teamPage = teamService.page(teamConvert.dto2entity(dto), dto.page());
    return Result.success(teamConvert.page2page(teamPage));
  }

  /**
   * 新增团队.
   *
   * @param dto 新增的团队信息
   * @return 新增后的团队信息
   */
  @PostMapping
  public Result<QuietTeamVO> save(@RequestBody @Validated(Create.class) QuietTeamDTO dto) {
    QuietTeam team =
        teamManager.saveOrUpdate(
            teamConvert.dto2entity(dto),
            userConvert.dto2entities(dto.getProductOwners()),
            userConvert.dto2entities(dto.getScrumMasters()),
            userConvert.dto2entities(dto.getMembers()));
    return Result.createSuccess(teamConvert.entity2vo(team));
  }

  /**
   * 删除团队.
   *
   * @param id 删除的团队ID
   * @return Result
   */
  @DeleteMapping("/{id}")
  @PreAuthorize(value = "hasRole('Admin')")
  public Result<Object> delete(@PathVariable Long id) {
    teamManager.deleteTeam(id);
    return Result.deleteSuccess();
  }

  /**
   * 更新团队.
   *
   * @param dto 更新的团队信息
   * @return 新增后的团队信息
   */
  @PutMapping
  public Result<QuietTeamVO> update(@RequestBody @Validated(Update.class) QuietTeamDTO dto) {
    QuietTeam team =
        teamManager.saveOrUpdate(
            teamConvert.dto2entity(dto),
            userConvert.dto2entities(dto.getProductOwners()),
            userConvert.dto2entities(dto.getScrumMasters()),
            userConvert.dto2entities(dto.getMembers()));
    return Result.updateSuccess(teamConvert.entity2vo(team));
  }
}

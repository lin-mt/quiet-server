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

import com.github.quiet.base.entity.QuietUserDetails;
import com.github.quiet.convert.system.QuietUserConvert;
import com.github.quiet.convert.system.QuietUserRoleConverter;
import com.github.quiet.dto.system.QuietUserDTO;
import com.github.quiet.dto.system.QuietUserRoleDTO;
import com.github.quiet.entity.system.QuietRole;
import com.github.quiet.entity.system.QuietUser;
import com.github.quiet.entity.system.QuietUserRole;
import com.github.quiet.manager.system.QuietUserManager;
import com.github.quiet.manager.system.QuietUserRoleManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.system.QuietUserRoleService;
import com.github.quiet.service.system.QuietUserService;
import com.github.quiet.utils.UserUtil;
import com.github.quiet.utils.ValidationUtils;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.PageValid;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.system.QuietUserRoleVO;
import com.github.quiet.vo.system.QuietUserVO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ?????? Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class QuietUserController {

  private final QuietUserService userService;
  private final QuietUserRoleService userRoleService;
  private final QuietUserRoleManager userRoleManager;
  private final QuietUserManager userManager;
  private final QuietUserConvert userConvert;
  private final QuietUserRoleConverter userRoleConverter;

  /**
   * ???????????????/????????????????????????
   *
   * @param keyword ?????????/??????
   * @return ????????????
   */
  @GetMapping("/list-users")
  public Result<List<QuietUserVO>> listUsers(
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) Set<Long> userIds) {
    List<QuietUser> users = userService.listUsers(keyword, userIds, 9);
    return Result.success(userConvert.entities2vos(users));
  }

  /**
   * ????????????.
   *
   * @param dto ????????????
   * @return ????????????????????????
   */
  @PostMapping
  public Result<QuietUserVO> create(@RequestBody @Validated(Create.class) QuietUserDTO dto) {
    // TODO ????????????????????????????????????????????????????????????
    QuietUser user = userService.save(userConvert.dto2entity(dto));
    return Result.createSuccess(userConvert.entity2vo(user));
  }

  /**
   * ??????????????????.
   *
   * @param id ??????ID
   * @return ????????????
   */
  @GetMapping("/{id}")
  public Result<QuietUserVO> get(@PathVariable Long id) {
    QuietUser user = userService.findById(id);
    return Result.createSuccess(userConvert.entity2vo(user));
  }

  /**
   * ??????????????????.
   *
   * @param dto ????????????
   * @return ?????????????????????
   */
  @GetMapping("/page")
  public Result<Page<QuietUserVO>> page(@Validated(PageValid.class) QuietUserDTO dto) {
    Page<QuietUser> userPage = userService.page(userConvert.dto2entity(dto), dto.page());
    if (CollectionUtils.isNotEmpty(userPage.getContent())) {
      Set<Long> userIds =
          userPage.getContent().stream().map(QuietUser::getId).collect(Collectors.toSet());
      Map<Long, List<QuietRole>> userIdToRoleInfo = userRoleManager.mapUserIdToRoleInfo(userIds);
      for (QuietUser user : userPage.getContent()) {
        user.setAuthorities(userIdToRoleInfo.get(user.getId()));
      }
    }
    return Result.success(userConvert.page2page(userPage));
  }

  /**
   * ????????????.
   *
   * @param id ??????????????????ID
   * @return ????????????
   */
  @DeleteMapping("/{id}")
  @PreAuthorize(value = "hasRole('Admin')")
  public Result<Object> delete(@PathVariable Long id) {
    userManager.delete(id);
    return Result.deleteSuccess();
  }

  /**
   * ????????????.
   *
   * @param dto :update ????????????????????????
   * @return ????????????????????????
   */
  @PutMapping
  @PreAuthorize(value = "#dto.id == authentication.principal.id || hasRole('Admin')")
  public Result<QuietUserVO> update(@RequestBody @Validated(Update.class) QuietUserDTO dto) {
    QuietUser update = userService.update(userConvert.dto2entity(dto));
    return Result.updateSuccess(userConvert.entity2vo(update));
  }

  /**
   * ???????????????????????????.
   *
   * @return ?????????????????????
   */
  @GetMapping("/current-user-info")
  public Result<QuietUserDetails> currentUserInfo() {
    return Result.success(UserUtil.get());
  }

  /**
   * ?????????????????????
   *
   * @param dto :id ????????????????????????ID :roleId ???????????????ID
   * @return ????????????
   */
  @PostMapping("/remove-role")
  public Result<Object> removeRole(@RequestBody QuietUserDTO dto) {
    ValidationUtils.notNull(dto.getId(), "userRole.useId.not.null");
    ValidationUtils.notNull(dto.getRoleId(), "userRole.roleId.not.null");
    userRoleService.deleteUserRole(dto.getId(), dto.getRoleId());
    return Result.deleteSuccess();
  }

  /**
   * ?????????????????????
   *
   * @param dto :id ????????????????????????ID :roleId ???????????????ID
   * @return ????????????
   */
  @PostMapping("/add-roles")
  public Result<List<QuietUserRoleVO>> addRoles(@RequestBody QuietUserRoleDTO dto) {
    List<QuietUserRole> quietUserRoles = userRoleConverter.dto2entities(dto.getUserRoles());
    List<QuietUserRole> userRoles = userRoleService.addRoles(quietUserRoles);
    return Result.createSuccess(userRoleConverter.entities2vos(userRoles));
  }

  /**
   * ?????????????????????
   *
   * @param userId ??????ID
   * @param roleIds ???????????????????????????Id??????
   * @return ????????????
   */
  @PostMapping("/update-roles/{userId}")
  public Result<List<QuietUserRoleVO>> updateRoles(
      @PathVariable Long userId, @RequestBody(required = false) Set<Long> roleIds) {
    userRoleService.updateRoles(userId, roleIds);
    return Result.updateSuccess();
  }

  /**
   * ????????????ID????????????????????????
   *
   * @param id ??????ID
   * @return ??????????????????
   */
  @GetMapping("/team/{id}")
  public Result<List<QuietUserVO>> listTeamUser(@PathVariable Long id) {
    List<QuietUser> users = userService.listTeamUser(id);
    return Result.success(userConvert.entities2vos(users));
  }
}

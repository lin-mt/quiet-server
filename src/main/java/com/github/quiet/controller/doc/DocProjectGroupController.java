/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.quiet.controller.doc;

import com.github.quiet.convert.doc.DocProjectGroupConverter;
import com.github.quiet.convert.doc.DocProjectGroupMemberConverter;
import com.github.quiet.dto.doc.DocProjectGroupDTO;
import com.github.quiet.dto.doc.DocProjectGroupMemberDTO;
import com.github.quiet.entity.doc.DocProjectGroup;
import com.github.quiet.entity.doc.DocProjectGroupMember;
import com.github.quiet.entity.system.QuietUser;
import com.github.quiet.manager.doc.DocProjectGroupManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.doc.DocProjectGroupService;
import com.github.quiet.service.system.QuietUserService;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.doc.DocProjectGroupMemberVO;
import com.github.quiet.vo.doc.DocProjectGroupVO;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/doc/project-group")
public class DocProjectGroupController {

  private final DocProjectGroupService projectGroupService;
  private final DocProjectGroupManager projectGroupManager;
  private final DocProjectGroupConverter converter;
  private final DocProjectGroupMemberConverter groupMemberConverter;
  private final QuietUserService userService;

  /**
   * ????????????????????????.
   *
   * @param name ????????????
   * @param groupIds ??????ID??????
   * @return ??????????????????
   */
  @GetMapping("/all")
  public Result<List<DocProjectGroupVO>> all(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Set<Long> groupIds) {
    List<DocProjectGroup> projectGroups = projectGroupService.listAll(name, groupIds);
    return Result.success(converter.entities2vos(projectGroups));
  }

  /**
   * ??????????????????????????????.
   *
   * @param groupId ????????????ID
   * @return ????????????????????????
   */
  @GetMapping("/all-member/{groupId}")
  public Result<List<DocProjectGroupMemberVO>> allMember(@PathVariable Long groupId) {
    List<DocProjectGroupMember> members = projectGroupManager.listMemberById(groupId);
    List<DocProjectGroupMemberVO> memberVos = groupMemberConverter.entities2vos(members);
    if (CollectionUtils.isNotEmpty(memberVos)) {
      Set<Long> userIds =
          memberVos.stream().map(DocProjectGroupMemberVO::getUserId).collect(Collectors.toSet());
      Map<Long, String> userId2Username =
          userService.findByUserIds(userIds).stream()
              .collect(Collectors.toMap(QuietUser::getId, QuietUser::getUsername));
      memberVos.forEach(member -> member.setUsername(userId2Username.get(member.getUserId())));
    }
    return Result.success(memberVos);
  }

  /**
   * ????????????????????????.
   *
   * @param dto ??????????????????
   * @return ????????????????????????
   */
  @PostMapping("/add-member")
  public Result<DocProjectGroupMemberVO> addMember(
      @RequestBody @Validated(Create.class) DocProjectGroupMemberDTO dto) {
    DocProjectGroupMember projectGroupMember =
        projectGroupManager.saveOrUpdateMember(groupMemberConverter.dto2entity(dto));
    return Result.success(groupMemberConverter.entity2vo(projectGroupMember));
  }

  /**
   * ????????????????????????.
   *
   * @param groupId ??????ID
   * @param userId ??????ID
   * @return ????????????
   */
  @DeleteMapping("/remove-member")
  public Result<Object> removeMember(@RequestParam Long groupId, @RequestParam Long userId) {
    projectGroupManager.removeMember(groupId, userId);
    return Result.deleteSuccess();
  }

  /**
   * ????????????????????????.
   *
   * @param dto ???????????????????????????
   * @return ????????????
   */
  @PostMapping("/update-member")
  public Result<DocProjectGroupMemberVO> updateMember(
      @RequestBody @Validated(Update.class) DocProjectGroupMemberDTO dto) {
    DocProjectGroupMember projectGroupMember =
        projectGroupManager.saveOrUpdateMember(groupMemberConverter.dto2entity(dto));
    return Result.success(groupMemberConverter.entity2vo(projectGroupMember));
  }

  /**
   * ??????????????????.
   *
   * @param dto ???????????????????????????
   * @return ??????????????????????????????
   */
  @PostMapping
  public Result<DocProjectGroupVO> save(
      @RequestBody @Validated(Create.class) DocProjectGroupDTO dto) {
    DocProjectGroup save = projectGroupService.saveOrUpdate(converter.dto2entity(dto));
    return Result.createSuccess(converter.entity2vo(save));
  }

  /**
   * ??????????????????.
   *
   * @param id ?????????????????????ID
   * @return Result
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    projectGroupManager.delete(id);
    return Result.deleteSuccess();
  }

  /**
   * ??????????????????.
   *
   * @param dto ???????????????????????????
   * @return ??????????????????????????????
   */
  @PutMapping
  public Result<DocProjectGroupVO> update(
      @RequestBody @Validated(Update.class) DocProjectGroupDTO dto) {
    DocProjectGroup update = projectGroupService.saveOrUpdate(converter.dto2entity(dto));
    return Result.updateSuccess(converter.entity2vo(update));
  }
}

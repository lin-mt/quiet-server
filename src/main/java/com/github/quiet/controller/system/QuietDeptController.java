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

import com.blazebit.persistence.PagedList;
import com.github.quiet.convert.system.QuietDeptConvert;
import com.github.quiet.convert.system.QuietUserConvert;
import com.github.quiet.dto.system.QuietDeptDTO;
import com.github.quiet.entity.system.QuietDept;
import com.github.quiet.entity.system.QuietUser;
import com.github.quiet.manager.system.QuietDeptManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.system.QuietDeptService;
import com.github.quiet.service.system.QuietDeptUserService;
import com.github.quiet.utils.EntityUtils;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.IdValid;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.system.QuietDeptVO;
import com.github.quiet.vo.system.QuietUserVO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ??????Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dept")
public class QuietDeptController {

  private final QuietDeptService deptService;
  private final QuietDeptManager deptManager;
  private final QuietDeptUserService deptUserService;
  private final QuietDeptConvert deptConvert;
  private final QuietUserConvert userConvert;

  /**
   * ????????????????????????.
   *
   * @param dto :id ??????????????????????????????ID :userIds ???????????????ID??????
   * @return ????????????
   */
  @PostMapping("/remove-users")
  public Result<Object> removeUsers(@RequestBody @Validated(IdValid.class) QuietDeptDTO dto) {
    deptUserService.removeUsers(dto.getId(), dto.getUserIds());
    return Result.success();
  }

  /**
   * ????????????????????????.
   *
   * @param dto :id ??????????????????????????????ID :userIds ???????????????ID??????
   * @return ????????????
   */
  @PostMapping("/add-users")
  public Result<Object> addUsers(@RequestBody @Validated(IdValid.class) QuietDeptDTO dto) {
    deptUserService.addUsers(dto.getId(), dto.getUserIds());
    return Result.success();
  }

  /**
   * ?????????????????????????????????.
   *
   * @param dto ????????????
   * @return ??????????????????????????????
   */
  @GetMapping("/page-user")
  public Result<Page<QuietUserVO>> pageUser(QuietDeptDTO dto) {
    QuietUser user = userConvert.dto2entity(dto.getParams());
    PagedList<QuietUser> pagedList = deptService.pageUser(dto.getId(), user, dto.page());
    return Result.success(userConvert.pageList2page(pagedList, dto.page()));
  }

  /**
   * ????????????????????????.
   *
   * @param dto ????????????
   * @return ?????????????????????
   */
  @GetMapping("/page")
  public Result<Page<QuietDeptVO>> page(QuietDeptDTO dto) {
    Page<QuietDept> deptPage = deptService.page(deptConvert.dto2entity(dto), dto.page());
    return Result.success(deptConvert.page2page(deptPage));
  }

  /**
   * ???????????????????????????????????????.
   *
   * @return ?????????????????????
   */
  @GetMapping("/tree")
  public Result<List<QuietDeptVO>> tree() {
    List<QuietDept> tree = deptService.findAll();
    return Result.success(EntityUtils.buildTreeData(deptConvert.entities2vos(tree)));
  }

  /**
   * ????????????.
   *
   * @param dto ?????????????????????
   * @return ?????????????????????
   */
  @PostMapping
  public Result<QuietDeptVO> save(@RequestBody @Validated(Create.class) QuietDeptDTO dto) {
    QuietDept dept = deptService.saveOrUpdate(deptConvert.dto2entity(dto));
    return Result.createSuccess(deptConvert.entity2vo(dept));
  }

  /**
   * ??????????????????.
   *
   * @param dto ?????????????????????
   * @return ????????????????????????
   */
  @PutMapping
  public Result<QuietDeptVO> update(@RequestBody @Validated(Update.class) QuietDeptDTO dto) {
    QuietDept update = deptService.saveOrUpdate(deptConvert.dto2entity(dto));
    return Result.updateSuccess(deptConvert.entity2vo(update));
  }

  /**
   * ??????????????????.
   *
   * @param id ?????????????????????ID
   * @return ????????????
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    deptManager.deleteById(id);
    return Result.deleteSuccess();
  }
}

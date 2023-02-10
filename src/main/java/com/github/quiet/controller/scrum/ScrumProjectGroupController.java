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

package com.github.quiet.controller.scrum;

import com.github.quiet.convert.scrum.ScrumProjectGroupConverter;
import com.github.quiet.dto.scrum.ScrumProjectGroupDTO;
import com.github.quiet.entity.scrum.ScrumProjectGroup;
import com.github.quiet.manager.scrum.ScrumProjectGroupManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.scrum.ScrumProjectGroupService;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.scrum.ScrumProjectGroupVO;
import lombok.AllArgsConstructor;
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
import java.util.Set;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/scrum/project-group")
public class ScrumProjectGroupController {
  private final ScrumProjectGroupService projectGroupService;
  private final ScrumProjectGroupManager projectGroupManager;
  private final ScrumProjectGroupConverter converter;

  /**
   * 查询所有项目分组.
   *
   * @param name 项目名称
   * @param groupIds 项目ID集合
   * @return 项目分组信息
   */
  @GetMapping("/list")
  public Result<List<ScrumProjectGroupVO>> list(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Set<Long> groupIds) {
    List<ScrumProjectGroup> projectGroups = projectGroupService.listAll(name, groupIds);
    return Result.success(converter.entities2vos(projectGroups));
  }

  /**
   * 新增项目分组.
   *
   * @param dto 新增的项目分组信息
   * @return 新增后的项目分组信息
   */
  @PostMapping
  public Result<ScrumProjectGroupVO> save(
      @RequestBody @Validated(Create.class) ScrumProjectGroupDTO dto) {
    ScrumProjectGroup save = projectGroupService.saveOrUpdate(converter.dto2entity(dto));
    return Result.createSuccess(converter.entity2vo(save));
  }

  /**
   * 删除项目分组.
   *
   * @param id 删除的项目分组ID
   * @return Result
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    projectGroupManager.delete(id);
    return Result.deleteSuccess();
  }

  /**
   * 更新项目分组.
   *
   * @param dto 更新的项目分组信息
   * @return 更新后的项目分组信息
   */
  @PutMapping
  public Result<ScrumProjectGroupVO> update(
      @RequestBody @Validated(Update.class) ScrumProjectGroupDTO dto) {
    ScrumProjectGroup update = projectGroupService.saveOrUpdate(converter.dto2entity(dto));
    return Result.updateSuccess(converter.entity2vo(update));
  }
}

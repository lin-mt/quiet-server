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

import com.github.quiet.convert.scrum.ScrumVersionConvert;
import com.github.quiet.dto.scrum.ScrumVersionDTO;
import com.github.quiet.entity.scrum.ScrumVersion;
import com.github.quiet.manager.scrum.ScrumVersionManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.scrum.ScrumVersionService;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.scrum.ScrumVersionVO;
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

/**
 * 项目版本信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/version")
public class ScrumVersionController {

  private final ScrumVersionService versionService;
  private final ScrumVersionManager versionManager;
  private final ScrumVersionConvert versionConvert;

  /**
   * 查询项目的所有版本信息
   *
   * @param projectId 项目ID
   * @return 项目的所有版本信息
   */
  @GetMapping("/tree")
  public Result<List<ScrumVersionVO>> tree(@RequestParam Long projectId) {
    List<ScrumVersion> scrumVersions = versionService.list(projectId);
    return Result.success(versionConvert.entities2vos(scrumVersions));
  }

  /**
   * 新建版本
   *
   * @param dto 新建的版本信息
   * @return 新建后的版本信息
   */
  @PostMapping
  public Result<ScrumVersionVO> save(@RequestBody @Validated(Create.class) ScrumVersionDTO dto) {
    ScrumVersion save = versionService.saveOrUpdate(versionConvert.dto2entity(dto));
    return Result.createSuccess(versionConvert.entity2vo(save));
  }

  /**
   * 更新版本信息
   *
   * @param dto 更新的版本信息
   * @return 更新后的版本信息
   */
  @PutMapping
  public Result<ScrumVersionVO> update(@RequestBody @Validated(Update.class) ScrumVersionDTO dto) {
    ScrumVersion update = versionService.saveOrUpdate(versionConvert.dto2entity(dto));
    return Result.updateSuccess(versionConvert.entity2vo(update));
  }

  /**
   * 删除版本
   *
   * @param id 删除的版本ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    versionManager.deleteById(id);
    return Result.deleteSuccess();
  }
}

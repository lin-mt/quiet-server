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

import com.github.quiet.convert.scrum.ScrumProjectConvert;
import com.github.quiet.dto.scrum.ScrumProjectDTO;
import com.github.quiet.entity.scrum.ScrumProject;
import com.github.quiet.manager.scrum.ScrumProjectManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.scrum.ScrumProjectService;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.scrum.ScrumProjectVO;
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
 * 项目Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/scrum/project")
public class ScrumProjectController {

  private final ScrumProjectService projectService;
  private final ScrumProjectManager projectManager;
  private final ScrumProjectConvert projectConvert;

  /**
   * 获取项目信息
   *
   * @param id 项目ID
   * @return 项目信息
   */
  @GetMapping("/{id}")
  public Result<ScrumProjectVO> projectInfo(@PathVariable Long id) {
    ScrumProject project = projectService.projectInfo(id);
    return Result.success(projectConvert.entity2vo(project));
  }

  /**
   * 获取项目信息
   *
   * @param groupId 项目组ID
   * @return 项目信息
   */
  @GetMapping("/list")
  public Result<List<ScrumProjectVO>> list(
      @RequestParam(required = false) Long groupId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Long id) {
    List<ScrumProject> projects = projectService.list(groupId, name, id);
    return Result.success(projectConvert.entities2vos(projects));
  }

  /**
   * 新增项目
   *
   * @param dto 新增的项目信息
   * @return 新增后的项目信息
   */
  @PostMapping
  public Result<ScrumProjectVO> save(@RequestBody @Validated(Create.class) ScrumProjectDTO dto) {
    ScrumProject save = projectManager.saveOrUpdate(projectConvert.dto2entity(dto));
    return Result.createSuccess(projectConvert.entity2vo(save));
  }

  /**
   * 更新项目
   *
   * @param dto 更新的项目信息
   * @return 更新后的项目信息
   */
  @PutMapping
  public Result<ScrumProjectVO> update(@RequestBody @Validated(Update.class) ScrumProjectDTO dto) {
    ScrumProject update = projectManager.saveOrUpdate(projectConvert.dto2entity(dto));
    return Result.updateSuccess(projectConvert.entity2vo(update));
  }

  /**
   * 删除项目
   *
   * @param id 删除的项目ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    projectManager.deleteById(id);
    return Result.deleteSuccess();
  }
}

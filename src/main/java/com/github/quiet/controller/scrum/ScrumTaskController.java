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

import com.github.quiet.convert.scrum.ScrumTaskConvert;
import com.github.quiet.dto.scrum.ScrumTaskDTO;
import com.github.quiet.entity.scrum.ScrumTask;
import com.github.quiet.manager.scrum.ScrumTaskManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.scrum.ScrumTaskService;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.scrum.ScrumTaskVO;
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
 * 任务Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class ScrumTaskController {

  private final ScrumTaskService taskService;
  private final ScrumTaskManager taskManager;
  private final ScrumTaskConvert taskConvert;

  /**
   * 查询任务信息
   *
   * @param demandIds 需求ID集合
   * @param executorIds 执行者ID集合
   * @return 任务集合
   */
  @GetMapping("/list")
  public Result<List<ScrumTaskVO>> list(
      @RequestParam(required = false) Set<Long> demandIds,
      @RequestParam(required = false) Set<Long> executorIds) {
    List<ScrumTask> tasks = taskService.list(demandIds, executorIds);
    return Result.success(taskConvert.entities2vos(tasks));
  }

  /**
   * 创建任务
   *
   * @param dto 创建的任务信息
   * @return 创建后的任务信息
   */
  @PostMapping
  public Result<ScrumTaskVO> save(@RequestBody @Validated(Create.class) ScrumTaskDTO dto) {
    ScrumTask save = taskManager.saveOrUpdate(taskConvert.dto2entity(dto));
    return Result.createSuccess(taskConvert.entity2vo(save));
  }

  /**
   * 更新任务
   *
   * @param dto 更新的任务信息
   * @return 更新后的任务信息
   */
  @PutMapping
  public Result<ScrumTaskVO> update(@RequestBody @Validated(Update.class) ScrumTaskDTO dto) {
    ScrumTask update = taskManager.saveOrUpdate(taskConvert.dto2entity(dto));
    return Result.updateSuccess(taskConvert.entity2vo(update));
  }

  /**
   * 删除任务
   *
   * @param id 删除的任务ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    taskService.deleteById(id);
    return Result.deleteSuccess();
  }
}

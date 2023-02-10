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

import com.github.quiet.convert.scrum.ScrumPriorityConvert;
import com.github.quiet.dto.scrum.ScrumPriorityDTO;
import com.github.quiet.entity.scrum.ScrumPriority;
import com.github.quiet.manager.scrum.ScrumPriorityManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.scrum.ScrumPriorityService;
import com.github.quiet.vo.scrum.ScrumPriorityVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 优先级 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/priority")
public class ScrumPriorityController {

  private final ScrumPriorityService priorityService;
  private final ScrumPriorityManager priorityManager;
  private final ScrumPriorityConvert priorityConvert;

  /**
   * 批量保存/更新优先级信息
   *
   * @param priorities 批量保存/更新的优先级信息
   * @return 保存后的优先级信息
   */
  @PostMapping("/batch")
  public Result<List<ScrumPriorityVO>> saveBatch(
      @RequestBody @NotEmpty List<@Valid ScrumPriorityDTO> priorities,
      @RequestParam Long templateId) {
    List<ScrumPriority> batch =
        priorityManager.saveBatch(
            templateId,
            priorities.stream().map(priorityConvert::dto2entity).collect(Collectors.toList()));
    return Result.success(priorityConvert.entities2vos(batch));
  }

  /**
   * 根据模板ID查询该模板ID下的所有优先级配置信息
   *
   * @param templateId 模板ID
   * @return 模板下的所有优先级配置信息
   */
  @GetMapping("/list")
  public Result<List<ScrumPriorityVO>> list(@RequestParam Long templateId) {
    List<ScrumPriority> priorities = priorityService.list(templateId);
    return Result.success(priorityConvert.entities2vos(priorities));
  }
}

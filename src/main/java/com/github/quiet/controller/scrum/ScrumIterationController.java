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

import com.github.quiet.convert.scrum.ScrumIterationConvert;
import com.github.quiet.dto.scrum.ScrumIterationDTO;
import com.github.quiet.entity.scrum.ScrumIteration;
import com.github.quiet.manager.scrum.ScrumIterationManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.scrum.ScrumIterationService;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.scrum.ScrumIterationVO;
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
 * 迭代 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/iteration")
public class ScrumIterationController {

  private final ScrumIterationService iterationService;
  private final ScrumIterationManager iterationManager;
  private final ScrumIterationConvert iterationConvert;

  /**
   * 新建迭代
   *
   * @param dto 新建的迭代信息
   * @return 新建后的迭代信息
   */
  @PostMapping
  public Result<ScrumIterationVO> save(
      @RequestBody @Validated(Create.class) ScrumIterationDTO dto) {
    ScrumIteration save = iterationManager.saveOrUpdate(iterationConvert.dto2entity(dto));
    return Result.createSuccess(iterationConvert.entity2vo(save));
  }

  /**
   * 更新迭代信息
   *
   * @param dto 更新的迭代信息
   * @return 更新后的迭代信息
   */
  @PutMapping
  public Result<ScrumIterationVO> update(
      @RequestBody @Validated(Update.class) ScrumIterationDTO dto) {
    ScrumIteration update = iterationManager.saveOrUpdate(iterationConvert.dto2entity(dto));
    return Result.updateSuccess(iterationConvert.entity2vo(update));
  }

  /**
   * 开始迭代
   *
   * @param id 开始迭代的迭代ID
   * @return 开始迭代的迭代信息
   */
  @PostMapping("/start")
  public Result<ScrumIterationVO> start(@RequestParam Long id) {
    ScrumIteration iteration = iterationManager.start(id);
    return Result.updateSuccess(iterationConvert.entity2vo(iteration));
  }

  /**
   * 结束迭代
   *
   * @param id 结束迭代的迭代ID
   * @param nextId 需求移入的下一个迭代ID
   * @return 结束迭代的迭代信息
   */
  @PostMapping("/end")
  public Result<ScrumIterationVO> end(
      @RequestParam Long id, @RequestParam(required = false) Long nextId) {
    ScrumIteration iteration = iterationManager.end(id, nextId);
    return Result.updateSuccess(iterationConvert.entity2vo(iteration));
  }

  /**
   * 删除迭代
   *
   * @param id 删除的迭代ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    iterationManager.deleteById(id);
    return Result.deleteSuccess();
  }

  /**
   * 获取迭代信息
   *
   * @param id 迭代ID
   * @return 迭代信息
   */
  @GetMapping("/{id}")
  public Result<ScrumIterationVO> get(@PathVariable Long id) {
    ScrumIteration iteration = iterationService.getById(id);
    return Result.success(iterationConvert.entity2vo(iteration));
  }

  /**
   * 查询迭代信息
   *
   * @param versionIds 版本ID集合
   * @return 迭代信息
   */
  @GetMapping("/list")
  public Result<List<ScrumIterationVO>> list(@RequestParam(required = false) Set<Long> versionIds) {
    List<ScrumIteration> iterations = iterationService.findAllByVersionIds(versionIds);
    return Result.success(iterationConvert.entities2vos(iterations));
  }
}

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

import com.github.quiet.convert.scrum.ScrumDemandConvert;
import com.github.quiet.dto.scrum.ScrumDemandDTO;
import com.github.quiet.entity.scrum.ScrumDemand;
import com.github.quiet.manager.scrum.ScrumDemandManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.scrum.ScrumDemandService;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.scrum.ScrumDemandVO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 需求Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/demand")
public class ScrumDemandController {

  private final ScrumDemandService demandService;
  private final ScrumDemandManager demandManager;
  private final ScrumDemandConvert demandConvert;

  /**
   * 删除需求
   *
   * @param id 删除的需求ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    demandManager.deleteById(id);
    return Result.deleteSuccess();
  }

  /**
   * 创建需求
   *
   * @param dto 创建的需求信息
   * @return 创建后的需求信息
   */
  @PostMapping
  public Result<ScrumDemandVO> save(@RequestBody @Validated(Create.class) ScrumDemandDTO dto) {
    ScrumDemand save = demandService.saveOrUpdate(demandConvert.dto2entity(dto));
    return Result.createSuccess(demandConvert.entity2vo(save));
  }

  /**
   * 更新需求
   *
   * @param dto 更新的需求信息
   * @return 更新后的需求信息
   */
  @PutMapping
  public Result<ScrumDemandVO> update(@RequestBody @Validated(Update.class) ScrumDemandDTO dto) {
    ScrumDemand update = demandService.saveOrUpdate(demandConvert.dto2entity(dto));
    return Result.updateSuccess(demandConvert.entity2vo(update));
  }

  /**
   * 根据迭代ID查询迭代下的需求信息，迭代ID为空，limit 则不超过 30
   *
   * @param iterationId 迭代ID
   * @param title 需求标题
   * @param priorityId 优先级ID
   * @param limit 查询数量
   * @return 需求信息
   */
  @GetMapping("/list")
  public Result<List<ScrumDemandVO>> list(
      @RequestParam(required = false) Long iterationId,
      @RequestParam(required = false) String title,
      @RequestParam(required = false) Long priorityId,
      @RequestParam(required = false) Long limit) {
    List<ScrumDemand> scrumDemands = demandService.list(iterationId, title, priorityId, limit);
    return Result.success(demandConvert.entities2vos(scrumDemands));
  }

  /**
   * 分页查询需求信息
   *
   * @param dto 查询参数
   * @return 查询结果
   */
  @GetMapping("/page")
  public Result<Page<ScrumDemandVO>> page(ScrumDemandDTO dto) {
    Page<ScrumDemand> page =
        demandService.page(demandConvert.dto2entity(dto), dto.getPlanned(), dto.page());
    Page<ScrumDemandVO> vos = demandConvert.page2page(page);
    vos.getContent().forEach(scrumDemandVO -> scrumDemandVO.setAutoSort(false));
    return Result.success(vos);
  }
}

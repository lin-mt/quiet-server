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

import com.github.quiet.convert.system.QuietDictConverter;
import com.github.quiet.dto.system.QuietDictDTO;
import com.github.quiet.entity.system.QuietDict;
import com.github.quiet.entity.system.QuietDictType;
import com.github.quiet.manager.system.QuietDictManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.system.QuietDictService;
import com.github.quiet.service.system.QuietDictTypeService;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.system.QuietDictVO;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
public class QuietDictController {

  private final QuietDictService dictService;

  private final QuietDictManager dictManager;

  private final QuietDictConverter dictConverter;

  private final QuietDictTypeService dictTypeService;

  /**
   * 根据数据字典类型查询数据字典.
   *
   * @param typeId 数据字典类型ID
   * @return 查询所有信息
   */
  @GetMapping("/enabled")
  public Result<List<QuietDictVO>> enabled(
      @RequestParam(required = false) Long typeId,
      @RequestParam(required = false) String serviceId,
      @RequestParam(required = false) String typeKey) {
    List<QuietDict> dictList = dictManager.list(true, typeId, serviceId, typeKey);
    List<QuietDictVO> dictVOS = dictConverter.entities2vos(dictList);
    List<QuietDictVO> result = new ArrayList<>(dictVOS.size());
    if (CollectionUtils.isNotEmpty(dictVOS)) {
      Map<String, QuietDictVO> key2info = new HashMap<>(dictVOS.size());
      for (QuietDictVO dictVO : dictVOS) {
        key2info.put(dictVO.getKey(), dictVO);
        if (dictVO.getKey().length() == 2) {
          result.add(dictVO);
          continue;
        }
        QuietDictVO parent =
            key2info.get(dictVO.getKey().substring(0, dictVO.getKey().length() - 2));
        if (parent.getChildren() == null) {
          parent.setChildren(new ArrayList<>());
        }
        parent.getChildren().add(dictVO);
      }
    }
    return Result.success(result);
  }

  /**
   * 分页查询数据字典.
   *
   * @return 查询所有信息
   */
  @GetMapping("/page")
  public Result<Page<QuietDictVO>> page(QuietDictDTO dto) {
    Page<QuietDict> dictPage = dictService.page(dictConverter.dto2entity(dto), dto.page());
    Page<QuietDictVO> page = dictConverter.page2page(dictPage);
    List<QuietDictVO> content = page.getContent();
    if (CollectionUtils.isNotEmpty(content)) {
      Set<Long> typeIds = content.stream().map(QuietDictVO::getTypeId).collect(Collectors.toSet());
      Map<Long, QuietDictType> id2info =
          dictTypeService.listByIds(typeIds).stream()
              .collect(Collectors.toMap(QuietDictType::getId, t -> t));
      for (QuietDictVO vo : content) {
        vo.setTypeName(id2info.get(vo.getTypeId()).getName());
      }
    }
    return Result.success(page);
  }

  /**
   * 新增数据字典.
   *
   * @param dto 新增的数据字典信息
   * @return 新增后的数据字典信息
   */
  @PostMapping
  @PreAuthorize(value = "hasRole('Admin')")
  public Result<QuietDictVO> save(@RequestBody @Validated(Create.class) QuietDictDTO dto) {
    QuietDict save = dictManager.saveOrUpdate(dictConverter.dto2entity(dto));
    return Result.createSuccess(dictConverter.entity2vo(save));
  }

  /**
   * 删除数据字典.
   *
   * @param id 删除的数据字典ID
   * @return Result
   */
  @DeleteMapping("/{id}")
  @PreAuthorize(value = "hasRole('Admin')")
  public Result<Object> delete(@PathVariable Long id) {
    dictService.deleteById(id);
    return Result.deleteSuccess();
  }

  /**
   * 更新数据字典.
   *
   * @param dto 更新的数据字典信息
   * @return 更新后的数据字典信息
   */
  @PutMapping
  @PreAuthorize(value = "hasRole('Admin')")
  public Result<QuietDictVO> update(@RequestBody @Validated(Update.class) QuietDictDTO dto) {
    QuietDict update = dictManager.saveOrUpdate(dictConverter.dto2entity(dto));
    return Result.updateSuccess(dictConverter.entity2vo(update));
  }
}

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

import com.github.quiet.convert.system.QuietDictTypeConvert;
import com.github.quiet.dto.system.QuietDictTypeDTO;
import com.github.quiet.entity.system.QuietDictType;
import com.github.quiet.manager.system.QuietDictTypeManager;
import com.github.quiet.result.Result;
import com.github.quiet.service.system.QuietDictTypeService;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.system.QuietDictTypeVO;
import lombok.AllArgsConstructor;
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

import java.util.List;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict-type")
public class QuietDictTypeController {

  private final QuietDictTypeService dictTypeService;
  private final QuietDictTypeManager dictTypeManager;
  private final QuietDictTypeConvert dictTypeConvert;

  /**
   * 查询启用的所有数据字典类型.
   *
   * @param name 数据字典名称
   * @return 启用的所有数据字典
   */
  @GetMapping("/enabled")
  public Result<List<QuietDictTypeVO>> enabled(@RequestParam(required = false) String name) {
    List<QuietDictType> dictTypePage = dictTypeService.listByEnabledAndName(true, name);
    return Result.success(dictTypeConvert.entities2vos(dictTypePage));
  }

  /**
   * 分页查询数据字典类型.
   *
   * @return 查询所有信息
   */
  @GetMapping("/page")
  public Result<Page<QuietDictTypeVO>> page(QuietDictTypeDTO dto) {
    Page<QuietDictType> dictTypePage =
        dictTypeService.page(dictTypeConvert.dto2entity(dto), dto.page());
    return Result.success(dictTypeConvert.page2page(dictTypePage));
  }

  /**
   * 新增数据字典类型.
   *
   * @param dto 新增的数据字典类型信息
   * @return 新增后的数据字典类型信息
   */
  @PostMapping
  @PreAuthorize(value = "hasRole('Admin')")
  public Result<QuietDictTypeVO> save(@RequestBody @Validated(Create.class) QuietDictTypeDTO dto) {
    QuietDictType save = dictTypeService.saveOrUpdate(dictTypeConvert.dto2entity(dto));
    return Result.createSuccess(dictTypeConvert.entity2vo(save));
  }

  /**
   * 删除数据字典类型.
   *
   * @param id 删除的数据字典类型ID
   * @return Result
   */
  @DeleteMapping("/{id}")
  @PreAuthorize(value = "hasRole('Admin')")
  public Result<Object> delete(@PathVariable Long id) {
    dictTypeManager.deleteById(id);
    return Result.deleteSuccess();
  }

  /**
   * 更新数据字典类型.
   *
   * @param dto 更新的数据字典类型信息
   * @return 更新后的数据字典类型信息
   */
  @PutMapping
  @PreAuthorize(value = "hasRole('Admin')")
  public Result<QuietDictTypeVO> update(
      @RequestBody @Validated(Update.class) QuietDictTypeDTO dto) {
    QuietDictType update = dictTypeService.saveOrUpdate(dictTypeConvert.dto2entity(dto));
    return Result.updateSuccess(dictTypeConvert.entity2vo(update));
  }
}

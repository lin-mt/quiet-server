/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.quiet.controller.doc;

import com.github.quiet.convert.doc.DocProjectEnvConverter;
import com.github.quiet.dto.doc.DocProjectEnvDTO;
import com.github.quiet.entity.doc.DocProjectEnv;
import com.github.quiet.result.Result;
import com.github.quiet.service.doc.DocProjectEnvService;
import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.github.quiet.vo.doc.DocProjectEnvVO;
import lombok.AllArgsConstructor;
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
 * 项目环境接口.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/project-env")
public class DocProjectEnvController {

  private final DocProjectEnvConverter converter;
  private final DocProjectEnvService service;

  /**
   * 创建项目环境
   *
   * @param dto 环境信息
   * @return 创建的环境信息
   */
  @PostMapping
  public Result<DocProjectEnvVO> save(@RequestBody @Validated(Create.class) DocProjectEnvDTO dto) {
    DocProjectEnv projectEnv = service.saveOrUpdate(converter.dto2entity(dto));
    return Result.createSuccess(converter.entity2vo(projectEnv));
  }

  /**
   * 更新项目环境配置
   *
   * @param dto 环境配置信息
   * @return 更新的环境信息
   */
  @PutMapping
  public Result<DocProjectEnvVO> update(
      @RequestBody @Validated(Update.class) DocProjectEnvDTO dto) {
    DocProjectEnv update = service.saveOrUpdate(converter.dto2entity(dto));
    return Result.updateSuccess(converter.entity2vo(update));
  }

  /**
   * 根据ID删除环境配置
   *
   * @param id 环境配置ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    service.deleteById(id);
    return Result.deleteSuccess();
  }

  /**
   * 根据项目ID查询所有环境配置信息
   *
   * @param projectId 项目ID
   * @return 项目环境配置信息
   */
  @GetMapping("/list/{projectId}")
  public Result<List<DocProjectEnvVO>> listByProjectId(@PathVariable Long projectId) {
    List<DocProjectEnv> docProjectEnvs = service.listByProjectId(projectId);
    return Result.success(converter.entities2vos(docProjectEnvs));
  }
}

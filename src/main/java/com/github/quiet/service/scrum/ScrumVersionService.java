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

package com.github.quiet.service.scrum;

import com.github.quiet.entity.scrum.ScrumVersion;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 项目版本信息service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumVersionService {

  /**
   * v 查询项目的版本信息，版本中包含迭代信息
   *
   * @param projectId 要查询的项目ID
   * @return 版本信息
   */
  List<ScrumVersion> list(@NotNull Long projectId);

  /**
   * 新建/更新版本信息
   *
   * @param entity 新建/更新的版本信息
   * @return 新建/更新后的版本信息
   */
  ScrumVersion saveOrUpdate(ScrumVersion entity);

  /**
   * 根据id获取版本信息
   *
   * @param id 版本ID
   * @return 版本信息
   */
  ScrumVersion getById(Long id);

  /**
   * 根据版本ID查询所有子版本
   *
   * @param id 版本ID
   * @return 子版本
   */
  List<ScrumVersion> listAllChildren(Long id);
}

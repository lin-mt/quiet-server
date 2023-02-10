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

import com.github.quiet.entity.scrum.ScrumProjectGroup;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumProjectGroupService {

  /**
   * 新增或更新项目分组
   *
   * @param entity 项目分组信息
   * @return 新增或更新后的项目分组信息
   */
  ScrumProjectGroup saveOrUpdate(ScrumProjectGroup entity);

  /**
   * 查询所有分组信息
   *
   * @param name 项目名称
   * @param groupIds 项目ID集合
   * @return 所有项目分组信息
   */
  List<ScrumProjectGroup> listAll(String name, Set<Long> groupIds);
}

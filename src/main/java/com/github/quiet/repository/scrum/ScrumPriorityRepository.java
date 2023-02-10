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

package com.github.quiet.repository.scrum;

import com.github.quiet.base.QuietRepository;
import com.github.quiet.entity.scrum.ScrumPriority;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 优先级repository
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumPriorityRepository extends QuietRepository<ScrumPriority> {

  /**
   * 根据模板ID查询优先级信息
   *
   * @param templateId 模板ID
   * @return 优先级信息
   */
  List<ScrumPriority> findAllByTemplateId(Long templateId);

  /**
   * 根据模板ID统计优先级数量
   *
   * @param templateId 模板ID
   * @return 优先级数量
   */
  long countByTemplateId(Long templateId);
}

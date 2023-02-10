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
import com.github.quiet.entity.scrum.ScrumTemplate;
import org.springframework.stereotype.Repository;

/**
 * 模板Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumTemplateRepository extends QuietRepository<ScrumTemplate> {

  /**
   * 根据模板名称查找模板信息
   *
   * @param name 模板名称
   * @return 模板信息
   */
  ScrumTemplate findByName(String name);
}

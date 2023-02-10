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

package com.github.quiet.manager.scrum;

import com.github.quiet.exception.ServiceException;
import com.github.quiet.repository.scrum.ScrumVersionRepository;
import com.github.quiet.service.scrum.ScrumIterationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumVersionManager {

  private final ScrumVersionRepository versionRepository;
  private final ScrumIterationService iterationService;

  /**
   * 根据版本id删除版本信息
   *
   * @param id 版本ID
   */
  public void deleteById(Long id) {
    if (versionRepository.countByParentId(id) > 0) {
      throw new ServiceException("version.hasChild.canNotDelete", id);
    }
    if (iterationService.countByVersionId(id) > 0) {
      throw new ServiceException("version.hasIteration.canNotDelete", id);
    }
    versionRepository.deleteById(id);
  }
}

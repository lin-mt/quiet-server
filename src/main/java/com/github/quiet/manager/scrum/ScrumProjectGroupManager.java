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
import com.github.quiet.repository.scrum.ScrumProjectGroupRepository;
import com.github.quiet.service.scrum.ScrumProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumProjectGroupManager {

  private final ScrumProjectService projectService;
  private final ScrumProjectGroupRepository projectGroupRepository;

  /**
   * 根据ID删除项目组
   *
   * @param id 项目组ID
   */
  public void delete(Long id) {
    Long count = projectService.countByGroupId(id);
    if (count > 0) {
      throw new ServiceException("projectGroup.canNot.delete.projectExist");
    }
    projectGroupRepository.deleteById(id);
  }
}

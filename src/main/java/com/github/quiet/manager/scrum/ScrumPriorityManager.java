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

import com.github.quiet.entity.scrum.ScrumPriority;
import com.github.quiet.exception.ServiceException;
import com.github.quiet.repository.scrum.ScrumPriorityRepository;
import com.github.quiet.service.scrum.ScrumDemandService;
import com.github.quiet.service.scrum.ScrumPriorityService;
import com.github.quiet.service.scrum.ScrumTemplateService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumPriorityManager {

  private final ScrumPriorityService priorityService;
  private final ScrumPriorityRepository priorityRepository;
  private final ScrumTemplateService templateService;
  private final ScrumDemandService demandService;

  /**
   * 批量更新/保存模板下的优先级信息
   *
   * @param templateId 模板ID
   * @param priorities 要更新/保存的优先级信息
   */
  @SuppressWarnings("DuplicatedCode")
  public List<ScrumPriority> saveBatch(Long templateId, List<ScrumPriority> priorities) {
    Set<Long> existIds =
        priorityService.list(templateId).stream()
            .map(ScrumPriority::getId)
            .collect(Collectors.toSet());
    Set<Long> updateIds =
        priorities.stream()
            .map(ScrumPriority::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    Set<Long> deleteIds =
        existIds.stream().filter(id -> !updateIds.contains(id)).collect(Collectors.toSet());
    if (CollectionUtils.isNotEmpty(deleteIds)) {
      if (demandService.countByPriorityIdIn(deleteIds) > 0) {
        throw new ServiceException("priority.hasDemand.canNotDelete", deleteIds);
      }
      priorityRepository.deleteAllById(deleteIds);
      if (deleteIds.size() == existIds.size()) {
        templateService.disable(templateId);
      }
    }
    Set<String> allNames = new HashSet<>();
    priorities.forEach(
        priority -> {
          allNames.add(priority.getName());
          priority.setTemplateId(templateId);
        });
    if (allNames.size() != priorities.size()) {
      throw new ServiceException("priority.templateId.name.repeat");
    }
    return priorityRepository.saveAll(priorities);
  }
}

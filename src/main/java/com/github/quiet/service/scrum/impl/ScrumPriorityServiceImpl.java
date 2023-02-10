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

package com.github.quiet.service.scrum.impl;

import com.github.quiet.entity.scrum.ScrumPriority;
import com.github.quiet.repository.scrum.ScrumPriorityRepository;
import com.github.quiet.service.scrum.ScrumPriorityService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumPriorityServiceImpl implements ScrumPriorityService {

  private final ScrumPriorityRepository priorityRepository;

  @Override
  public void deleteByTemplateId(Long templateId) {
    List<ScrumPriority> priorities = priorityRepository.findAllByTemplateId(templateId);
    if (CollectionUtils.isNotEmpty(priorities)) {
      priorityRepository.deleteAll(priorities);
    }
  }

  @Override
  public List<ScrumPriority> list(Long templateId) {
    return priorityRepository.findAllByTemplateId(templateId);
  }

  @Override
  public long countByTemplateId(Long templateId) {
    return priorityRepository.countByTemplateId(templateId);
  }
}

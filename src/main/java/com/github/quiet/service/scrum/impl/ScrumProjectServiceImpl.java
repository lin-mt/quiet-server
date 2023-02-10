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

import com.github.quiet.entity.scrum.ScrumProject;
import com.github.quiet.exception.ServiceException;
import com.github.quiet.repository.scrum.ScrumProjectRepository;
import com.github.quiet.service.scrum.ScrumProjectService;
import com.github.quiet.utils.SelectBooleanBuilder;
import com.github.quiet.utils.UserUtil;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.github.quiet.entity.scrum.QScrumProject.scrumProject;

/**
 * 项目Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumProjectServiceImpl implements ScrumProjectService {

  private final ScrumProjectRepository projectRepository;

  @Override
  public long countByTemplateId(Long templateId) {
    return projectRepository.countByTemplateId(templateId);
  }

  @Override
  public ScrumProject findById(Long id) {
    return projectRepository
        .findById(id)
        .orElseThrow(() -> new ServiceException("project.id.not.exist", id));
  }

  @Override
  public ScrumProject projectInfo(Long id) {
    return findById(id);
  }

  @Override
  public List<ScrumProject> list(Long groupId, String name, Long id) {
    BooleanBuilder where =
        SelectBooleanBuilder.booleanBuilder()
            .isIdEq(id, scrumProject.id)
            .isIdEq(groupId, scrumProject.groupId)
            .with(
                builder -> {
                  if (groupId == null) {
                    builder
                        .and(scrumProject.groupId.isNull())
                        .and(scrumProject.creator.eq(UserUtil.getId()));
                  }
                })
            .getPredicate();
    return StreamSupport.stream(projectRepository.findAll(where).spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public Long countByGroupId(Long groupId) {
    return projectRepository.countByGroupId(groupId);
  }
}

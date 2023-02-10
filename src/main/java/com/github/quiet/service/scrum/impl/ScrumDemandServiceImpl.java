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

import com.github.quiet.entity.scrum.ScrumDemand;
import com.github.quiet.exception.ServiceException;
import com.github.quiet.repository.scrum.ScrumDemandRepository;
import com.github.quiet.service.scrum.ScrumDemandService;
import com.github.quiet.utils.SelectBooleanBuilder;
import com.github.quiet.utils.SelectBuilder;
import com.github.quiet.validation.groups.Create;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

import static com.github.quiet.entity.scrum.QScrumDemand.scrumDemand;

/**
 * 需求信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumDemandServiceImpl implements ScrumDemandService {

  private final ScrumDemandRepository demandRepository;
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<ScrumDemand> list(Long iterationId, String title, Long priorityId, Long limit) {
    if (iterationId == null && (limit == null || limit > 30 || limit < 0)) {
      limit = 30L;
    }
    BooleanBuilder predicate =
        SelectBooleanBuilder.booleanBuilder()
            .isIdEq(iterationId, scrumDemand.iterationId)
            .notBlankContains(title, scrumDemand.title)
            .isIdEq(priorityId, scrumDemand.priorityId)
            .getPredicate();
    JPAQuery<ScrumDemand> query = jpaQueryFactory.selectFrom(scrumDemand).where(predicate);
    if (limit != null) {
      query.limit(limit);
    }
    return query.fetch();
  }

  @Override
  public Page<ScrumDemand> page(ScrumDemand params, Boolean planned, Pageable page) {
    BooleanBuilder predicate =
        SelectBuilder.booleanBuilder(params)
            .with(
                builder -> {
                  if (planned != null) {
                    if (planned) {
                      builder.and(scrumDemand.iterationId.isNotNull());
                    } else {
                      builder.and(scrumDemand.iterationId.isNull());
                    }
                  }
                })
            .getPredicate();
    return demandRepository.findAll(predicate, page);
  }

  @Override
  public ScrumDemand saveOrUpdate(@Validated(Create.class) @NotNull ScrumDemand entity) {
    ScrumDemand exist =
        demandRepository.findByProjectIdAndTitle(entity.getProjectId(), entity.getTitle());
    if (exist != null && !exist.getId().equals(entity.getId())) {
      throw new ServiceException("demand.in.project.title.exist", entity.getTitle());
    }
    if (entity.getParentId() != null && !demandRepository.existsById(entity.getParentId())) {
      throw new ServiceException("demand.parentId.not.exist", entity.getParentId());
    }
    return demandRepository.saveAndFlush(entity);
  }

  @Override
  public long countByPriorityIdIn(Set<Long> priorityIds) {
    if (CollectionUtils.isEmpty(priorityIds)) {
      return 0L;
    }
    return demandRepository.countByPriorityIdIn(priorityIds);
  }

  @Override
  public long countByIterationId(Long iterationId) {
    return demandRepository.countByIterationId(iterationId);
  }

  @Override
  public ScrumDemand getById(Long id) {
    return demandRepository
        .findById(id)
        .orElseThrow(() -> new ServiceException("demand.id.notExist", id));
  }

  @Override
  public List<ScrumDemand> findAllUnfinished(Long iterationId) {
    return demandRepository.findAllByIterationIdAndEndTimeIsNull(iterationId);
  }

  @Override
  public void saveAll(List<ScrumDemand> demands) {
    demandRepository.saveAll(demands);
  }
}

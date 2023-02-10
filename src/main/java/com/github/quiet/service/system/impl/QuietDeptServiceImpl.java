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

package com.github.quiet.service.system.impl;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.PagedList;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.github.quiet.entity.system.QuietDept;
import com.github.quiet.entity.system.QuietUser;
import com.github.quiet.exception.ServiceException;
import com.github.quiet.repository.system.QuietDeptRepository;
import com.github.quiet.service.system.QuietDeptService;
import com.github.quiet.utils.SelectBuilder;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.github.quiet.entity.system.QQuietDeptUser.quietDeptUser;
import static com.github.quiet.entity.system.QQuietUser.quietUser;

/**
 * 部门Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietDeptServiceImpl implements QuietDeptService {

  private final QuietDeptRepository deptRepository;
  private final CriteriaBuilderFactory criteriaBuilderFactory;
  private final EntityManager entityManager;

  @Override
  public Page<QuietDept> page(QuietDept params, @NotNull Pageable page) {
    BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
    return deptRepository.findAll(predicate, page);
  }

  @Override
  public QuietDept saveOrUpdate(@NotNull QuietDept dept) {
    if (dept.getParentId() != null) {
      if (!deptRepository.existsById(dept.getParentId())) {
        throw new ServiceException("dept.not.exit.id", dept.getParentId());
      }
      QuietDept exist = deptRepository.getByDeptName(dept.getDeptName());
      if (exist != null && !exist.getId().equals(dept.getId())) {
        throw new ServiceException("dept.deptName.exist", dept.getDeptName());
      }
    }
    return deptRepository.saveAndFlush(dept);
  }

  @Override
  public List<QuietDept> findAll() {
    return deptRepository.findAll();
  }

  @Override
  public PagedList<QuietUser> pageUser(
      @NotNull Long deptId, QuietUser params, @NotNull Pageable page) {
    BooleanBuilder builder = SelectBuilder.booleanBuilder(params).getPredicate();
    builder.and(quietDeptUser.deptId.eq(deptId));
    return new BlazeJPAQuery<QuietUser>(entityManager, criteriaBuilderFactory)
        .select(quietUser)
        .from(quietUser)
        .leftJoin(quietDeptUser)
        .on(quietUser.id.eq(quietDeptUser.userId))
        .where(builder)
        .orderBy(quietUser.id.desc())
        .fetchPage((int) page.getOffset(), page.getPageSize());
  }
}

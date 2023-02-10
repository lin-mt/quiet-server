/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.quiet.service.doc.impl;

import com.github.quiet.entity.doc.DocProjectGroup;
import com.github.quiet.entity.doc.DocProjectGroupMember;
import com.github.quiet.enums.Permission;
import com.github.quiet.exception.ServiceException;
import com.github.quiet.repository.doc.DocProjectGroupMemberRepository;
import com.github.quiet.repository.doc.DocProjectGroupRepository;
import com.github.quiet.service.doc.DocProjectGroupService;
import com.github.quiet.utils.SelectBooleanBuilder;
import com.github.quiet.utils.UserUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.github.quiet.entity.doc.QDocProjectGroup.docProjectGroup;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class DocProjectGroupServiceImpl implements DocProjectGroupService {

  private final DocProjectGroupRepository repository;
  private final DocProjectGroupMemberRepository projectGroupMemberRepository;
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public DocProjectGroup saveOrUpdate(DocProjectGroup entity) {
    DocProjectGroup exist = repository.findByName(entity.getName());
    if (exist != null && !exist.getId().equals(entity.getId())) {
      throw new ServiceException("projectGroup.name.exist", entity.getName());
    }
    DocProjectGroup projectGroup = repository.saveAndFlush(entity);
    if (entity.getId() == null) {
      // 新建项目组，新增当前创建人为组长
      DocProjectGroupMember projectGroupMember = new DocProjectGroupMember();
      projectGroupMember.setGroupId(projectGroup.getId());
      projectGroupMember.setUserId(UserUtil.getId());
      projectGroupMember.setPermission(Permission.GROUP_LEADER);
      projectGroupMemberRepository.saveAndFlush(projectGroupMember);
    }
    return projectGroup;
  }

  @Override
  public List<DocProjectGroup> listAll(String name, Set<Long> groupIds) {
    BooleanBuilder where =
        SelectBooleanBuilder.booleanBuilder()
            .notBlankContains(name, docProjectGroup.name)
            .notEmptyIn(groupIds, docProjectGroup.id)
            .getPredicate();
    return jpaQueryFactory.selectFrom(docProjectGroup).where(where).fetch();
  }
}

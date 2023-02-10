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

import com.github.quiet.entity.system.QuietDeptUser;
import com.github.quiet.repository.system.QuietDeptUserRepository;
import com.github.quiet.service.system.QuietDeptUserService;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 部门成员信息 service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietDeptUserServiceImpl implements QuietDeptUserService {

  private final QuietDeptUserRepository deptUserRepository;

  public QuietDeptUserServiceImpl(QuietDeptUserRepository deptUserRepository) {
    this.deptUserRepository = deptUserRepository;
  }

  @Override
  public List<QuietDeptUser> listAllByDeptId(@NotNull Long deptId) {
    return deptUserRepository.findAllByDeptId(deptId);
  }

  @Override
  public void deleteByUserId(@NotNull Long userId) {
    deptUserRepository.deleteByUserId(userId);
  }

  @Override
  public void addUsers(@NotNull Long deptId, @NotNull Set<Long> userIds) {
    List<QuietDeptUser> deptUsers = this.listAllByDeptId(deptId);
    if (CollectionUtils.isNotEmpty(deptUsers)) {
      Set<Long> existUserIds =
          deptUsers.stream().map(QuietDeptUser::getUserId).collect(Collectors.toSet());
      userIds.removeAll(existUserIds);
    }
    if (CollectionUtils.isNotEmpty(userIds)) {
      List<QuietDeptUser> newUserInfo = new ArrayList<>(userIds.size());
      for (Long userId : userIds) {
        newUserInfo.add(new QuietDeptUser(deptId, userId));
      }
      deptUserRepository.saveAll(newUserInfo);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void removeUsers(@NotNull Long deptId, @NotEmpty Set<Long> userIds) {
    deptUserRepository.deleteAllByDeptIdAndUserIdIsIn(deptId, userIds);
  }
}

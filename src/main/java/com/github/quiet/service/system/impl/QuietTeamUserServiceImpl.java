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

import com.github.quiet.entity.system.QuietTeamUser;
import com.github.quiet.repository.system.QuietTeamUserRepository;
import com.github.quiet.service.system.QuietTeamUserService;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 团队成员信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietTeamUserServiceImpl implements QuietTeamUserService {

  private final QuietTeamUserRepository teamUserRepository;

  @Override
  public Map<Long, List<QuietTeamUser>> mapTeamIdToTeamUsers(@NotNull @NotEmpty Set<Long> teamIds) {
    return teamUserRepository.findByTeamIdIsIn(teamIds).stream()
        .collect(Collectors.groupingBy(QuietTeamUser::getTeamId));
  }

  @Override
  public List<QuietTeamUser> findAllUsersByTeamIds(@NotNull @NotEmpty Set<Long> teamIds) {
    return teamUserRepository.findByTeamIdIsIn(teamIds);
  }

  @Override
  public void addUsers(@NotNull Long teamId, Set<Long> userIds) {
    if (CollectionUtils.isEmpty(userIds)) {
      return;
    }
    Set<Long> allExistUserIds =
        this.findAllUsersByTeamIds(Set.of(teamId)).stream()
            .map(QuietTeamUser::getUserId)
            .collect(Collectors.toSet());
    if (CollectionUtils.isNotEmpty(allExistUserIds)) {
      userIds.removeAll(allExistUserIds);
      if (userIds.isEmpty()) {
        return;
      }
    }
    List<QuietTeamUser> quietTeamUsers = new ArrayList<>(userIds.size());
    for (Long memberId : userIds) {
      quietTeamUsers.add(new QuietTeamUser(teamId, memberId));
    }
    teamUserRepository.saveAll(quietTeamUsers);
  }

  @Override
  public List<QuietTeamUser> findByTeamIdAndUserIds(
      @NotNull Long teamId, @NotNull @NotEmpty Set<Long> userIds) {
    return teamUserRepository.findAllByTeamIdAndUserIdIsIn(teamId, userIds);
  }

  @Override
  public List<QuietTeamUser> findAllByUserId(@NotNull Long userId) {
    return teamUserRepository.findAllByUserId(userId);
  }

  @Override
  public List<QuietTeamUser> findByTeamId(Long id) {
    return teamUserRepository.findAllByTeamId(id);
  }

  @Override
  public List<QuietTeamUser> findAllByTeamId(Long teamId) {
    return teamUserRepository.findAllByTeamId(teamId);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByTeamId(Long teamId) {
    teamUserRepository.deleteByTeamId(teamId);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByUserId(Long userId) {
    teamUserRepository.deleteByUserId(userId);
  }
}

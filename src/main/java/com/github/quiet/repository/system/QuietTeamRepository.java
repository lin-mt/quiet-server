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

package com.github.quiet.repository.system;

import com.github.quiet.base.QuietRepository;
import com.github.quiet.entity.system.QuietTeam;
import org.springframework.stereotype.Repository;

/**
 * 团队Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietTeamRepository extends QuietRepository<QuietTeam> {

  /**
   * 根据团队名称查询团队信息
   *
   * @param teamName 团队名称
   * @return 团队信息
   */
  QuietTeam getByTeamName(String teamName);
}

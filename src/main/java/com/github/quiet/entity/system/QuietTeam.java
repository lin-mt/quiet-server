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

package com.github.quiet.entity.system;

import com.github.quiet.base.entity.BaseEntity;
import com.github.quiet.utils.SelectBuilder;
import com.querydsl.core.BooleanBuilder;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import static com.github.quiet.entity.system.QQuietTeam.quietTeam;

/**
 * 团队.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "quiet_team")
public class QuietTeam extends BaseEntity {

  /** 团队名称 */
  @NotBlank
  @Length(max = 8)
  @Column(name = "team_name", nullable = false, length = 16)
  private String teamName;

  /** 标语 */
  @Column(name = "slogan", length = 60)
  @Length(max = 60)
  private String slogan;

  @Nullable
  @Override
  public BooleanBuilder booleanBuilder() {
    return SelectBuilder.booleanBuilder()
        .isIdEq(getId(), quietTeam.id)
        .notBlankContains(getTeamName(), quietTeam.teamName)
        .notBlankContains(getSlogan(), quietTeam.slogan)
        .getPredicate();
  }
}

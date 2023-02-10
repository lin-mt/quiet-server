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

import com.github.quiet.base.entity.Dict;
import com.github.quiet.utils.SelectBooleanBuilder;
import com.querydsl.core.BooleanBuilder;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import static com.github.quiet.entity.system.QQuietDict.quietDict;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "quiet_dict")
public class QuietDict extends Dict {

  /** 备注 */
  @Length(max = 100)
  @Column(name = "remark", length = 100)
  private String remark;

  @Nullable
  @Override
  public BooleanBuilder booleanBuilder() {
    return SelectBooleanBuilder.booleanBuilder()
        .isIdEq(getId(), quietDict.id)
        .isIdEq(getTypeId(), quietDict.typeId)
        .notBlankEq(getKey(), quietDict.key)
        .notBlankContains(getName(), quietDict.name)
        .notNullEq(getEnabled(), quietDict.enabled)
        .getPredicate();
  }
}

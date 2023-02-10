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

package com.github.quiet.dto.scrum;

import com.github.quiet.annotation.ExistId;
import com.github.quiet.base.dto.SortableDTO;
import com.github.quiet.base.entity.Sortable;
import com.github.quiet.repository.scrum.ScrumVersionRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 迭代信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumIterationDTO extends SortableDTO {

  /** 迭代名称 */
  @NotBlank
  @Length(max = 30)
  private String name;

  /** 所属版本ID */
  @NotNull
  @ExistId(
      repository = ScrumVersionRepository.class,
      message = "{quiet.validation.version.id.notExist}")
  private Long versionId;

  /** 迭代计划开始日期 */
  @NotNull private LocalDate planStartDate;

  /** 迭代计划结束日期 */
  @NotNull private LocalDate planEndDate;

  /** 迭代开始时间 */
  private LocalDateTime startTime;

  /** 迭代结束时间 */
  private LocalDateTime endTime;

  /** 备注信息 */
  @Length(max = 1000)
  private String remark;

  @Override
  public int compareTo(@Nullable Sortable other) {
    int compare = super.compareTo(other);
    if (compare == 0 && other instanceof ScrumIterationDTO otherIteration) {
      compare = planStartDate.compareTo(otherIteration.getPlanStartDate());
      if (compare == 0) {
        compare = getGmtCreate().compareTo(otherIteration.getGmtCreate());
      }
    }
    return compare;
  }
}

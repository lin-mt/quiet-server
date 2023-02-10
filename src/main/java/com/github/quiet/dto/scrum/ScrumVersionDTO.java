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
import com.github.quiet.base.dto.ParentAndSortableDTO;
import com.github.quiet.base.entity.Sortable;
import com.github.quiet.repository.scrum.ScrumProjectRepository;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 项目的版本信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumVersionDTO extends ParentAndSortableDTO<ScrumVersionDTO> {

  /** 版本名称 */
  @NotBlank
  @Length(max = 10)
  private String name;

  /** 所属项目ID */
  @NotNull
  @ExistId(
      repository = ScrumProjectRepository.class,
      message = "{quiet.validation.project.id.notExist}")
  private Long projectId;

  /** 计划开始日期 */
  @NotNull private LocalDate planStartDate;

  /** 计划结束日期 */
  @NotNull private LocalDate planEndDate;

  /** 版本开始时间 */
  private LocalDateTime startTime;

  /** 版本结束时间 */
  private LocalDateTime endTime;

  /** 版本备注信息 */
  @Length(max = 1500)
  private String remark;

  @Override
  public int compareTo(@Nullable Sortable other) {
    int compare = super.compareTo(other);
    if (compare == 0 && other instanceof ScrumVersionDTO otherVersion) {
      compare = planStartDate.compareTo(otherVersion.getPlanStartDate());
      if (compare == 0) {
        compare = getGmtCreate().compareTo(otherVersion.getGmtCreate());
      }
    }
    return compare;
  }
}

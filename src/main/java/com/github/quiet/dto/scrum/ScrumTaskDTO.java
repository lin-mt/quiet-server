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

import com.github.quiet.base.dto.SortableDTO;
import com.github.quiet.base.entity.Dict;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 任务信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumTaskDTO extends SortableDTO {
  /** 任务标题 */
  @NotBlank
  @Length(max = 10)
  private String title;

  /** 任务类型 */
  @NotNull private Dict type;

  /** 所属需求ID */
  @NotNull private Long demandId;

  /** 任务的当前步骤ID */
  @NotNull private Long taskStepId;

  /** 执行者 */
  @NotNull private Long executorId;

  /** 参与者（最多20人参与） */
  @Size(max = 20)
  private Set<Long> participant;

  /** 前置任务 */
  @Size(max = 20)
  private Set<Long> preTaskIds;

  /** 任务开始时间 */
  private LocalDateTime startTime;

  /** 任务结束时间 */
  private LocalDateTime endTime;

  /** 任务备注信息 */
  @Length(max = 3000)
  private String remark;
}

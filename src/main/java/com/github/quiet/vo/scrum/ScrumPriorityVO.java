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

package com.github.quiet.vo.scrum;

import com.github.quiet.base.vo.SortableVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 优先级.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumPriorityVO extends SortableVO {

  /** 优先级名称 */
  @NotBlank
  @Length(max = 10)
  private String name;

  /** 图标的十六进制颜色/arco颜色 */
  @Length(max = 25)
  @Pattern(
      regexp =
          "^(#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3}))|(rgb\\(var\\(--(red|orangered|orange|gold|yellow|lime|green|cyan|blue|arcoblue|purple|pinkpurple|magenta)-([1-9]|10)\\)\\))$")
  private String color;

  /** 模板ID */
  @NotNull private Long templateId;

  /** 备注信息 */
  @Length(max = 100)
  private String remark;
}

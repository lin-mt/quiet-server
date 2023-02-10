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

package com.github.quiet.dto.system;

import com.github.quiet.base.dto.SortableDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietDictDTO extends SortableDTO {

  /** 字典类型ID */
  @NotNull private Long typeId;

  /** 字典key，格式为每层级占两位数字，第一层级范围：00-99，第二层级的前两位为第一层级的key， 所以第二层级范围为：0000-9999，后续层级以此类推 */
  @NotBlank
  @Length(min = 2, max = 18)
  private String key;

  /** 名称 */
  @NotBlank private String name;

  /** 是否启用 */
  @NotNull private Boolean enabled;

  /** 备注 */
  private String remark;
}

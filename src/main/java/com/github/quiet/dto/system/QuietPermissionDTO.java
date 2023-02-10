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

import com.github.quiet.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 权限.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietPermissionDTO extends BaseDTO {

  /** 应用名称 */
  @NotBlank
  @Length(max = 100)
  private String applicationName;

  /** URL 匹配规则 */
  @NotBlank
  @Length(max = 100)
  private String urlPattern;

  /** 请求方法 */
  @NotBlank
  @Length(max = 7)
  private String requestMethod;

  /** 角色ID */
  @NotNull private Long roleId;

  /** 备注 */
  @Length(max = 100)
  private String remark;
}

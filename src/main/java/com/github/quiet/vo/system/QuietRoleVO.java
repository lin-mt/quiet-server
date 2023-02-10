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

package com.github.quiet.vo.system;

import com.github.quiet.base.vo.ParentVO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 角色信息VO.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietRoleVO extends ParentVO<QuietRoleVO> {

  /** 角色名称 */
  @NotBlank
  @Length(max = 30)
  private String roleName;

  /** 角色中文名 */
  @NotBlank
  @Length(max = 30)
  private String roleCnName;

  /** 备注 */
  @Length(max = 100)
  private String remark;

  /** 父角色名称 */
  private String parentRoleName;
}

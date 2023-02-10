/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.quiet.entity.doc;

import com.github.quiet.base.entity.BaseEntity;
import com.github.quiet.enums.Permission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 项目组成员.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_project_group_member")
public class DocProjectGroupMember extends BaseEntity {

  /** 分组ID */
  @NotNull
  @Column(name = "group_id", nullable = false)
  private Long groupId;

  /** 用户ID */
  @NotNull
  @Column(name = "user_id", nullable = false)
  private Long userId;

  /** 权限 */
  @NotNull
  @Column(name = "permission", nullable = false)
  private Permission permission;
}

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
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import static com.github.quiet.entity.system.QQuietPermission.quietPermission;

/**
 * 权限.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "quiet_permission")
public class QuietPermission extends BaseEntity {

  /** 应用名称 */
  @NotBlank
  @Length(max = 100)
  @Column(name = "application_name", nullable = false, length = 100)
  private String applicationName;

  /** URL 匹配规则 */
  @NotBlank
  @Length(max = 100)
  @Column(name = "url_pattern", length = 100)
  private String urlPattern;

  /** 请求方法 */
  @NotBlank
  @Length(max = 7)
  @Column(name = "request_method", length = 7)
  private String requestMethod;

  /** 角色ID */
  @NotNull
  @Column(name = "role_id", nullable = false)
  private Long roleId;

  /** 备注 */
  @Length(max = 100)
  @Column(name = "remark", length = 100)
  private String remark;

  @Nullable
  @Override
  public BooleanBuilder booleanBuilder() {
    return SelectBuilder.booleanBuilder()
        .isIdEq(getId(), quietPermission.id)
        .isIdEq(getRoleId(), quietPermission.roleId)
        .notBlankEq(getRequestMethod(), quietPermission.requestMethod)
        .notBlankContains(getApplicationName(), quietPermission.applicationName)
        .notBlankContains(getUrlPattern(), quietPermission.urlPattern)
        .notBlankContains(getRemark(), quietPermission.remark)
        .getPredicate();
  }
}

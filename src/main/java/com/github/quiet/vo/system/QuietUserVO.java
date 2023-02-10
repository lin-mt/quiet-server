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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.quiet.base.entity.QuietGrantedAuthority;
import com.github.quiet.base.vo.BaseVO;
import com.github.quiet.constant.service.RoleNames;
import com.github.quiet.entity.system.QuietUserRole;
import com.github.quiet.enums.Gender;
import com.github.quiet.json.annotation.JsonHasRole;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import java.util.Collection;
import java.util.List;

/**
 * 用户信息VO.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietUserVO extends BaseVO {

  /** 用户名 */
  @NotBlank
  @Length(max = 10)
  private String username;

  /** 全名（姓名） */
  @NotBlank
  @Length(max = 10)
  private String fullName;

  /** 头像地址 */
  @Length(max = 100)
  private String avatar;

  /** 密码 */
  @NotBlank
  @Length(max = 60)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String secretCode;

  /** 性别 */
  private Gender gender;

  /** 电话号码（手机号码） */
  @Pattern(regexp = "^1\\d{10}$", message = "{user.phoneNumber.wrong}")
  @Length(min = 11, max = 11, message = "{user.phoneNumber.wrong}")
  private String phoneNumber;

  /** 邮箱地址 */
  @Email private String emailAddress;

  /** 账号是否过期 */
  @ColumnDefault("0")
  @JsonHasRole(RoleNames.Admin)
  private Boolean accountExpired;

  /** 账号是否被锁 */
  @JsonHasRole(RoleNames.Admin)
  private Boolean accountLocked;

  /** 密码是否过期 */
  @JsonHasRole(RoleNames.Admin)
  private Boolean credentialsExpired;

  /** 账号是否启用 */
  @JsonHasRole(RoleNames.Admin)
  private Boolean enabled;

  /** 角色信息 */
  @Transient
  private Collection<? extends QuietGrantedAuthority<? extends QuietGrantedAuthority<?>>>
      authorities;

  /** 角色ID */
  private Long roleId;

  /** 用户与角色信息 */
  private List<QuietUserRole> userRoles;
}

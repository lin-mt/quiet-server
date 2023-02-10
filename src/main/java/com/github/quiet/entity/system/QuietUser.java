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

import com.github.quiet.base.entity.QuietUserDetails;
import com.github.quiet.utils.SelectBuilder;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import static com.github.quiet.entity.system.QQuietUser.quietUser;

/**
 * 用户信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_user")
public class QuietUser extends QuietUserDetails {

  @Override
  public BooleanBuilder booleanBuilder() {
    return SelectBuilder.booleanBuilder()
        .isIdEq(getId(), quietUser.id)
        .notBlankContains(getUsername(), quietUser.username)
        .notBlankContains(getFullName(), quietUser.fullName)
        .notNullEq(getGender(), quietUser.gender)
        .notBlankContains(getPhoneNumber(), quietUser.phoneNumber)
        .notBlankContains(getEmailAddress(), quietUser.emailAddress)
        .notNullEq(getAccountExpired(), quietUser.accountExpired)
        .notNullEq(getAccountLocked(), quietUser.accountLocked)
        .notNullEq(getCredentialsExpired(), quietUser.credentialsExpired)
        .notNullEq(getEnabled(), quietUser.enabled)
        .getPredicate();
  }
}

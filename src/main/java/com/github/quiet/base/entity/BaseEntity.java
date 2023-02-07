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

package com.github.quiet.base.entity;

import com.github.quiet.validation.groups.Create;
import com.github.quiet.validation.groups.Update;
import com.querydsl.core.BooleanBuilder;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体类的公共属性.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
public class BaseEntity implements Serializable {

  @Id
  @Null(groups = Create.class)
  @NotNull(groups = Update.class)
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdGenerator")
  @GenericGenerator(name = "IdGenerator", strategy = "com.github.quiet.jpa.IdGenerator")
  private Long id;

  @CreatedBy
  @Column(name = "creator", updatable = false)
  private Long creator;

  @LastModifiedBy
  @Column(name = "updater", insertable = false)
  private Long updater;

  @CreatedDate
  @Column(name = "gmt_create", updatable = false)
  private LocalDateTime gmtCreate;

  @LastModifiedDate
  @Column(name = "gmt_update", insertable = false)
  private LocalDateTime gmtUpdate;

  @Nullable
  public BooleanBuilder booleanBuilder() {
    return null;
  }
}

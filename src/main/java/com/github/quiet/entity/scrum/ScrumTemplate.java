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

package com.github.quiet.entity.scrum;

import com.github.quiet.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

/**
 * 项目模板.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "scrum_template")
public class ScrumTemplate extends BaseEntity {

  /** 模板名称 */
  @NotBlank
  @Length(max = 10)
  @Column(name = "template_name", nullable = false, length = 10)
  private String name;

  /** 是否启用，true：项目可以选择该模板，false：项目新建的时候不可以选择该模块 */
  @ColumnDefault("0")
  @Column(name = "enabled", columnDefinition = "TINYINT(1)")
  private Boolean enabled;

  /** 模板备注信息 */
  @Length(max = 30)
  @Column(name = "remark", length = 30)
  private String remark;
}

/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.quiet.entity.doc;

import com.github.quiet.base.entity.BaseEntity;
import com.github.quiet.model.Cookie;
import com.github.quiet.model.Header;
import com.github.quiet.model.HttpProtocol;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 项目环境配置
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_project_env")
public class DocProjectEnv extends BaseEntity {

  /** 环境名称 */
  @NotEmpty
  @Length(max = 30)
  @Column(name = "env_name", nullable = false, length = 30)
  private String name;

  /** 项目ID */
  @NotNull
  @Column(name = "project_id", nullable = false)
  private Long projectId;

  /** http协议 */
  @NotNull
  @Column(name = "protocol", nullable = false)
  private HttpProtocol protocol;

  /** 域名 */
  @Length(max = 90)
  @Column(name = "domain", length = 90)
  private String domain;

  /** 请求头 */
  @Type(JsonType.class)
  @Column(name = "header", columnDefinition = "json")
  private List<Header> headers;

  /** 请求cookie */
  @Type(JsonType.class)
  @Column(name = "cookie", columnDefinition = "json")
  private List<Cookie> cookies;
}

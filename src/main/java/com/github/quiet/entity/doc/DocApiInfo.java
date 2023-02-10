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
import com.github.quiet.model.FormParam;
import com.github.quiet.model.Header;
import com.github.quiet.model.PathParam;
import com.github.quiet.model.QueryParam;
import com.github.quiet.model.Schema;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;

/**
 * api 信息，包含请求参数、请求头等信息.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_api_info")
public class DocApiInfo extends BaseEntity {

  /** 文档ID */
  @NotNull
  @Column(name = "api_id")
  private Long apiId;

  /** 路径参数 */
  @Type(JsonType.class)
  @Column(name = "path_param", columnDefinition = "json")
  private List<PathParam> pathParam;

  /** 请求体的 jsonSchema */
  @Type(JsonType.class)
  @Column(name = "req_json_body", columnDefinition = "json")
  private Schema reqJsonBody;

  /** form 参数 */
  @Type(JsonType.class)
  @Column(name = "req_form", columnDefinition = "json")
  private List<FormParam> reqForm;

  /** 请求文件 */
  @Column(name = "req_file", length = 200)
  private String reqFile;

  /** raw */
  @Column(name = "req_raw", length = 2000)
  private String reqRaw;

  /** query 参数 */
  @Type(JsonType.class)
  @Column(name = "req_query", columnDefinition = "json")
  private List<QueryParam> reqQuery;

  /** 请求头 */
  @Type(JsonType.class)
  @Column(name = "headers", columnDefinition = "json")
  private List<Header> headers;

  /*
   * 响应数据的 jsonSchema
   */
  @Type(JsonType.class)
  @Column(name = "resp_json_body", columnDefinition = "json")
  private Schema respJsonBody;

  /** 响应信息 */
  @Column(name = "resp_raw", length = 3000)
  private String respRaw;
}

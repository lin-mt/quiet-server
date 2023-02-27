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

package com.github.quiet.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.quiet.base.quiet.QuietMessage;
import com.github.quiet.result.Result;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 返回 json 数据.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public abstract class AbstractResponseJsonData implements QuietMessage {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * 返回 json 格式数据.
   *
   * @param response HttpServletResponse
   * @param result result
   * @param <T> 数据类型
   * @throws IOException 数据异常
   */
  protected <T> void responseJsonData(HttpServletResponse response, Result<T> result)
      throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.write(getObjectMapper().writeValueAsString(result));
    out.flush();
    out.close();
  }

  @NotNull
  abstract ObjectMapper getObjectMapper();
}

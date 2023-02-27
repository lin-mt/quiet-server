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

package com.github.quiet.base.quiet;

import com.github.quiet.utils.MessageSourceUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietMessage {

  default String getMessage(
      HttpServletRequest request, MessageSource messageSource, String code, Object... param) {
    return MessageSourceUtil.getMessage(request, messageSource, code, param);
  }
}

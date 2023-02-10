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

package com.github.quiet.converter;

import com.github.quiet.base.entity.Dict;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * String 转数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class StringToDictConverter implements Converter<String, Dict> {

  @Override
  public Dict convert(@Nullable String source) {
    if (StringUtils.isBlank(source)) {
      return null;
    }
    Dict dict = new Dict();
    dict.setKey(source);
    return dict;
  }
}

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

package com.github.quiet.converter.jpa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.quiet.utils.JsonUtils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 实体Set属性转换数据库String.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter(autoApply = true)
public class SetStringConverter implements AttributeConverter<Set<String>, String> {

  private static final TypeReference<Set<String>> reference = new TypeReference<>() {};

  @Override
  public String convertToDatabaseColumn(Set<String> attribute) {
    if (CollectionUtils.isEmpty(attribute)) {
      return null;
    }
    return JsonUtils.toJsonString(attribute);
  }

  @Override
  public Set<String> convertToEntityAttribute(String dbData) {
    Set<String> attribute = new HashSet<>();
    if (StringUtils.isBlank(dbData)) {
      return attribute;
    }
    return JsonUtils.readValue(dbData, reference);
  }
}

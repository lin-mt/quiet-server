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

package cn.linmt.quiet.converter;

import cn.linmt.quiet.modal.jpa.enums.ByteEnum;
import cn.linmt.quiet.modal.jpa.enums.DoubleEnum;
import cn.linmt.quiet.modal.jpa.enums.FloatEnum;
import cn.linmt.quiet.modal.jpa.enums.IntegerEnum;
import cn.linmt.quiet.modal.jpa.enums.LongEnum;
import cn.linmt.quiet.modal.jpa.enums.ShortEnum;
import cn.linmt.quiet.modal.jpa.enums.StringEnum;
import lombok.Getter;

@Getter
public enum EnumType {
  BYTE(ByteEnum.class, Byte.class),
  DOUBLE(DoubleEnum.class, Double.class),
  FLOAT(FloatEnum.class, Float.class),
  INTEGER(IntegerEnum.class, Integer.class),
  LONG(LongEnum.class, Long.class),
  SHORT(ShortEnum.class, Short.class),
  STRING(StringEnum.class, String.class),
  ;

  private final Class<?> superClass;

  private final Class<?> valueClass;

  EnumType(Class<?> superClass, Class<?> valueClass) {
    this.superClass = superClass;
    this.valueClass = valueClass;
  }
}

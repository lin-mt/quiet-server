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

package cn.linmt.quiet.modal.jpa.base;

import jakarta.annotation.Nullable;

/**
 * 可排序.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface Sortable extends Comparable<Sortable> {

  /**
   * 获取排序的序号
   *
   * @return 序号
   */
  int getOrdinal();

  /**
   * 设置排序的序号
   *
   * @param ordinal 序号
   */
  void setOrdinal(int ordinal);

  /**
   * 跟其他对象进行比较
   *
   * @param other 比较的对象
   * @return 比较结果
   */
  @Override
  default int compareTo(@Nullable Sortable other) {
    if (other == null) {
      return 1;
    }
    return Integer.compare(getOrdinal(), other.getOrdinal());
  }
}

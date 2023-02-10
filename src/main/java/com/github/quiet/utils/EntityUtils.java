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

package com.github.quiet.utils;

import com.github.quiet.base.vo.ParentVO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 实体类工具.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class EntityUtils {

  private EntityUtils() {}

  /**
   * 构建树形结构的数据
   *
   * @param source 要构建的原数据
   * @param <T> 实体
   * @return 构建后的数据
   */
  public static <T extends ParentVO<T>> List<T> buildTreeData(List<T> source) {
    List<T> treeData = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(source)) {
      Map<Long, T> idToEntity = source.stream().collect(Collectors.toMap(ParentVO::getId, p -> p));
      Set<Long> keys = idToEntity.keySet();
      for (T datum : source) {
        if (datum.getParentId() == null || !keys.contains(datum.getParentId())) {
          treeData.add(datum);
          continue;
        }
        idToEntity.get(datum.getParentId()).addChildren(datum);
      }
    }
    return treeData;
  }
}

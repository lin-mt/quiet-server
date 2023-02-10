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

package com.github.quiet.base.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 带有父子关系的 VO.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ParentVO<T extends ParentVO<T>> extends BaseVO {

  private Long parentId;

  private List<T> children;

  /**
   * 添加子级信息
   *
   * @param children 子级信息
   */
  public void addChildren(T children) {
    if (null == getChildren()) {
      setChildren(new ArrayList<>());
      if (getChildren() == null) {
        throw new IllegalStateException("设置子级信息后仍为 null");
      }
    }
    getChildren().add(children);
  }
}

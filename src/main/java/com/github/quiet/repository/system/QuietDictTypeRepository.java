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

package com.github.quiet.repository.system;

import com.github.quiet.base.QuietRepository;
import com.github.quiet.entity.system.QuietDictType;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietDictTypeRepository extends QuietRepository<QuietDictType> {

  /**
   * 根据数据字典类型 key 查询数据类型信息
   *
   * @param serviceId 服务ID
   * @param key 类型
   * @return 数据字典类型信息
   */
  QuietDictType findByServiceIdAndKey(String serviceId, String key);
}

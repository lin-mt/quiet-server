/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.quiet.manager.doc;

import com.github.quiet.repository.doc.DocApiInfoRepository;
import com.github.quiet.repository.doc.DocApiRepository;
import com.github.quiet.service.doc.DocApiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class DocApiManager {

  private final DocApiService apiService;
  private final DocApiRepository apiRepository;
  private final DocApiInfoRepository apiInfoRepository;

  /**
   * 根据接口ID删除接口信息
   *
   * @param id 接口ID
   */
  @Transactional(rollbackFor = Exception.class)
  public void deleteById(Long id) {
    apiService.getById(id);
    apiRepository.deleteById(id);
    apiInfoRepository.deleteByApiId(id);
  }
}

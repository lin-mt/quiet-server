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

package com.github.quiet.manager.scrum;

import com.github.quiet.entity.scrum.ScrumProject;
import com.github.quiet.exception.ServiceException;
import com.github.quiet.repository.scrum.ScrumProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumProjectManager {

  private final ScrumProjectRepository projectRepository;

  /**
   * 根据项目ID删除项目信息
   *
   * @param id 要删除的项目的ID
   */
  @Transactional(rollbackFor = Exception.class)
  public void deleteById(@NotNull Long id) {
    // 删除任务信息
    // 删除需求信息
    // 删除迭代信息
    // 删除版本信息
    // 删除项目信息
    // TODO 备份项目信息
    throw new UnsupportedOperationException(String.format("不支持删除项目:%s", id));
  }

  /**
   * 新增或更新项目信息
   *
   * @param entity 要新增/更新的项目信息
   * @return 新增/更新后的项目信息
   */
  public ScrumProject saveOrUpdate(ScrumProject entity) {
    ScrumProject scrumProject =
        projectRepository.findByGroupIdAndName(entity.getGroupId(), entity.getName());
    if (scrumProject != null) {
      if (!scrumProject.getId().equals(entity.getId())) {
        throw new ServiceException(
            "project.group-id.name.exist", entity.getGroupId(), entity.getName());
      }
      if (!scrumProject.getTemplateId().equals(entity.getTemplateId())) {
        // TODO 处理任务信息、需求信息
        throw new UnsupportedOperationException("不支持变更模板");
      }
    }
    return projectRepository.saveAndFlush(entity);
  }
}

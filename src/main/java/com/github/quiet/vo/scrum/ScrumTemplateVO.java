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

package com.github.quiet.vo.scrum;

import com.github.quiet.base.vo.BaseVO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 项目模板.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumTemplateVO extends BaseVO {

  /** 模板中的任务步骤 */
  List<ScrumTaskStepVO> taskSteps;

  /** 模板中的优先级配置 */
  List<ScrumPriorityVO> priorities;

  /** 模板名称 */
  @NotBlank
  @Length(max = 10)
  private String name;

  /** 是否启用，true：项目可以选择该模板，false：项目新建的时候不可以选择该模块 */
  private Boolean enabled;

  /** 模板备注信息 */
  @Length(max = 30)
  private String remark;
}

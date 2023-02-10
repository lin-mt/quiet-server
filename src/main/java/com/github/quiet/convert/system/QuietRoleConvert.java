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

package com.github.quiet.convert.system;

import com.github.quiet.base.dto.QuietConvert;
import com.github.quiet.dto.system.QuietRoleDTO;
import com.github.quiet.entity.system.QuietRole;
import com.github.quiet.vo.system.QuietRoleVO;
import org.mapstruct.Mapper;

/**
 * 权限信息实体信息转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Mapper
public interface QuietRoleConvert extends QuietConvert<QuietRole, QuietRoleDTO, QuietRoleVO> {}

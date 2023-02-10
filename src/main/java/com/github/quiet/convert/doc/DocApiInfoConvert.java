/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.quiet.convert.doc;

import com.github.quiet.base.dto.QuietConvert;
import com.github.quiet.dto.doc.DocApiInfoDTO;
import com.github.quiet.entity.doc.DocApiInfo;
import com.github.quiet.vo.doc.DocApiInfoVO;
import org.mapstruct.Mapper;

/**
 * api信息转换工具.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Mapper
public interface DocApiInfoConvert extends QuietConvert<DocApiInfo, DocApiInfoDTO, DocApiInfoVO> {}

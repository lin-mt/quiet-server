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

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class ClassUtils {

  private ClassUtils() {}

  public static Class<?> deduceMainClass() {
    return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
        .walk(ClassUtils::findMainClass)
        .orElse(null);
  }

  private static Optional<Class<?>> findMainClass(Stream<StackWalker.StackFrame> stack) {
    return stack
        .filter((frame) -> Objects.equals(frame.getMethodName(), "main"))
        .findFirst()
        .map(StackWalker.StackFrame::getDeclaringClass);
  }
}

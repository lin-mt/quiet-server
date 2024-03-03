package cn.linmt.quiet.util;

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

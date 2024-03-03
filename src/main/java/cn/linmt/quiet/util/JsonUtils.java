package cn.linmt.quiet.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public final class JsonUtils {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private JsonUtils() {}

  @SneakyThrows
  public static <T> String toString(T object) {
    return OBJECT_MAPPER.writeValueAsString(object);
  }
}

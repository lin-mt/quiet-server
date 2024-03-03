package cn.linmt.quiet.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class LongToStringSerializer extends JsonSerializer<Long> {

  @Override
  public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    if (value != null) {
      gen.writeString(String.valueOf(value));
    }
  }
}

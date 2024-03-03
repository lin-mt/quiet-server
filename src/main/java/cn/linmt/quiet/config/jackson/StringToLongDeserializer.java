package cn.linmt.quiet.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class StringToLongDeserializer extends JsonDeserializer<Long> {

  @Override
  public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    String value = p.getValueAsString();
    if (value != null) {
      return Long.valueOf(value);
    }
    return null;
  }
}

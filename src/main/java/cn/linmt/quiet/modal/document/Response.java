package cn.linmt.quiet.modal.document;

import java.util.List;
import java.util.Objects;
import lombok.Data;

@Data
public class Response {

  private int statusCode;

  private List<HttpHeader> headers;

  private String jsonSchema;

  private String body;

  private String description;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Response response)) return false;
    return getStatusCode() == response.getStatusCode();
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getStatusCode());
  }
}

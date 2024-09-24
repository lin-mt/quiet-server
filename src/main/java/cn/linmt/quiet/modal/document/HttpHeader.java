package cn.linmt.quiet.modal.document;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class HttpHeader {

  @NotBlank
  private String key;

  private List<String> values;

  private String description;
}

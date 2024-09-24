package cn.linmt.quiet.modal.document;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UrlEncodeParam {

  @NotBlank
  private String key;

  private String value;

  private String description;
}

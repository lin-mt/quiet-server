package cn.linmt.quiet.modal.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormDataParam {

  @NotBlank private String key;

  @NotNull private FormDataType type;
  private String value;
  private String contentType;
  private String description;

  enum FormDataType {
    TEXT,
    FILE
  }
}

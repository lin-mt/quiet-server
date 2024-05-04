package cn.linmt.quiet.modal;

import cn.linmt.quiet.modal.enums.FormType;
import lombok.Data;

@Data
public class ApiForm {

  private String name;

  private String example;

  private FormType type;

  private Boolean required;

  private String description;
}

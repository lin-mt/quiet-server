package cn.linmt.quiet.modal;

import lombok.Data;

@Data
public class ApiHeader {

  private String name;

  private String example;

  private Boolean required;

  private String description;
}

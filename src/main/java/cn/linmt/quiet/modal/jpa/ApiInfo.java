package cn.linmt.quiet.modal.jpa;

import cn.linmt.quiet.enums.HttpMethod;
import lombok.Data;

@Data
public class ApiInfo {

  private HttpMethod method;

  private String path;
}

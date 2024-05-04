package cn.linmt.quiet.modal;

import cn.linmt.quiet.enums.HttpMethod;
import lombok.Data;

@Data
public class ApiInfo {

  private HttpMethod method;

  private String path;
}

package cn.linmt.quiet.modal;

import java.util.Set;
import lombok.Data;

@Data
public class ApiDetail {

  private Set<ApiHeader> headers;

  private ApiBody body;
}

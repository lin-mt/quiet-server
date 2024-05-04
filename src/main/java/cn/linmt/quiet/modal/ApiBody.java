package cn.linmt.quiet.modal;

import java.util.Set;
import lombok.Data;

@Data
public class ApiBody {

  private Set<ApiForm> forms;

  private Set<ApiQuery> queries;

  private String body;
}

package cn.linmt.quiet.modal.document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;

@Data
public class ApiEndpointSpec {

  @NotNull private Request request;

  @NotEmpty private Set<Response> responses;
}

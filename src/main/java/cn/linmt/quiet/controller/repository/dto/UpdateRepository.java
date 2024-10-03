package cn.linmt.quiet.controller.repository.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateRepository extends AddRepository {

  @NotNull private Long id;
}

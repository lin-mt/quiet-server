package cn.linmt.quiet.controller.version.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateVersion extends AddVersion {
  @NotNull
  @Schema(description = "版本ID")
  private Long id;
}

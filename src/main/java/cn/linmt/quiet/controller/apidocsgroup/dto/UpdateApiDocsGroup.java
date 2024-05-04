package cn.linmt.quiet.controller.apidocsgroup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateApiDocsGroup extends SaveApiDocsGroup {

  @NotNull
  @Schema(description = "分组ID")
  private Long id;
}

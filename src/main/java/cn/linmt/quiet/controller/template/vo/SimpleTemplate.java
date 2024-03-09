package cn.linmt.quiet.controller.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SimpleTemplate {

  @NotNull
  @Schema(description = "模板ID")
  private Long id;

  @NotBlank
  @Schema(description = "模板名称")
  private String name;
}

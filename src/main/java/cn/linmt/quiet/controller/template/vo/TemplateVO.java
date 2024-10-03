package cn.linmt.quiet.controller.template.vo;

import cn.linmt.quiet.controller.DisabledVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateVO extends DisabledVO {

  @NotNull
  @Schema(description = "模板ID")
  private Long id;

  @NotBlank
  @Schema(description = "模板名称")
  private String name;

  @Schema(description = "模板描述")
  private String description;
}

package cn.linmt.quiet.controller.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RequirementPriorityVO {

  @NotNull
  @Schema(description = "优先级ID")
  private Long id;

  @NotBlank
  @Length(max = 30)
  @Schema(description = "优先级名称")
  private String name;

  @NotBlank
  @Schema(description = "卡片颜色")
  private String color;

  @Schema(description = "优先级描述")
  private String description;
}

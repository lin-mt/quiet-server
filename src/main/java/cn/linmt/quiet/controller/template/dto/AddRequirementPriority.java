package cn.linmt.quiet.controller.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddRequirementPriority {

  @NotBlank
  @Length(max = 30)
  @Schema(description = "优先级名称")
  private String name;

  @NotBlank
  @Length(max = 16)
  @Schema(description = "卡片颜色")
  private String color;

  @Length(max = 255)
  @Schema(description = "优先级描述")
  private String description;
}

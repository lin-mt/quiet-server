package cn.linmt.quiet.controller.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RequirementTypeVO {

  @NotNull
  @Schema(description = "需求类型ID")
  private Long id;

  @NotBlank
  @Length(max = 30)
  @Schema(description = "需求类型名称")
  private String name;

  @Schema(description = "需求类型描述")
  private String description;
}

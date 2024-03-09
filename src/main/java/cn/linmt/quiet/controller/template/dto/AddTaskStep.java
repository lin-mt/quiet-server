package cn.linmt.quiet.controller.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddTaskStep {

  @NotBlank
  @Length(max = 30)
  @Schema(description = "步骤名称")
  private String name;

  @Length(max = 255)
  @Schema(description = "步骤描述")
  private String description;
}

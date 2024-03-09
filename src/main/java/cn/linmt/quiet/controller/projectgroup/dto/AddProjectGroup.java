package cn.linmt.quiet.controller.projectgroup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddProjectGroup {

  @NotBlank
  @Length(max = 30)
  @Schema(description = "项目组名称")
  private String name;

  @Length(max = 255)
  @Schema(description = "项目组描述")
  private String description;
}

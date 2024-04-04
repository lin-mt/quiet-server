package cn.linmt.quiet.controller.template.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddTaskType {

  @NotBlank
  @Length(max = 30)
  @Schema(description = "任务类型名称")
  private String name;

  @Schema(description = "是否为后端接口任务")
  private Boolean backendApi;

  @Length(max = 255)
  @Schema(description = "任务类型描述")
  private String description;
}

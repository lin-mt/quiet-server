package cn.linmt.quiet.controller.template.vo;

import cn.linmt.quiet.controller.DisabledVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskTypeVO extends DisabledVO {

  @NotNull
  @Schema(description = "任务类型ID")
  private Long id;

  @NotBlank
  @Schema(description = "任务类型名称")
  private String name;

  @Schema(description = "是否为后端接口任务")
  private Boolean backendApi;

  @Schema(description = "任务类型描述")
  private String description;
}

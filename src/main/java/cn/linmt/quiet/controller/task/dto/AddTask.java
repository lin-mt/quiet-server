package cn.linmt.quiet.controller.task.dto;

import cn.linmt.quiet.modal.ApiInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddTask {

  @NotBlank
  @Schema(description = "任务标题")
  private String title;

  @NotNull
  @Schema(description = "任务类型")
  private Long typeId;

  @NotNull
  @Schema(description = "需求ID")
  private Long requirementId;

  @NotNull
  @Schema(description = "项目ID")
  private Long projectId;

  @NotNull
  @Schema(description = "报告人")
  private Long reporterId;

  @NotNull
  @Schema(description = "处理人")
  private Long handlerId;

  @Schema(description = "接口信息")
  private ApiInfo apiInfo;

  @Length(max = 255)
  @Schema(description = "描述")
  private String description;
}

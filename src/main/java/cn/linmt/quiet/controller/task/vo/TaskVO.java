package cn.linmt.quiet.controller.task.vo;

import cn.linmt.quiet.modal.Api;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TaskVO {

  @NotNull
  @Schema(description = "ID")
  private Long id;

  @NotBlank
  @Schema(description = "任务标题")
  private String title;

  @NotNull
  @Schema(description = "任务类型")
  private Long typeId;

  @Schema(description = "接口信息")
  private Api api;

  @NotNull
  @Schema(description = "当前所在的任务步骤ID")
  private Long taskStepId;

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

  @Length(max = 255)
  @Schema(description = "描述")
  private String description;
}

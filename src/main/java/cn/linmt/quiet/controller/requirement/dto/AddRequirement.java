package cn.linmt.quiet.controller.requirement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddRequirement {

  @NotBlank
  @Schema(description = "需求标题")
  @Length(max = 30)
  private String title;

  @NotNull
  @Schema(description = "需求类型")
  private Long typeId;

  @NotNull
  @Schema(description = "优先级ID")
  private Long priorityId;

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

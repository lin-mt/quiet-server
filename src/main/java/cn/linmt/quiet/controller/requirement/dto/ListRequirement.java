package cn.linmt.quiet.controller.requirement.dto;

import cn.linmt.quiet.enums.RequirementStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ListRequirement {

  @Length(max = 30)
  @Schema(description = "需求标题")
  private String title;

  @NotNull
  @Schema(description = "项目ID")
  private Long projectId;

  @Schema(description = "需求类型")
  private Long typeId;

  @Schema(description = "优先级ID")
  private Long priorityId;

  @Schema(description = "需求状态")
  private RequirementStatus status;

  @Min(0)
  @NotNull
  @Schema(description = "跳过几条数据")
  private Long offset;

  @Min(1)
  @NotNull
  @Schema(description = "查询条数")
  private Long limit;
}

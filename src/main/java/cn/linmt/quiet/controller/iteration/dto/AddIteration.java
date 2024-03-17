package cn.linmt.quiet.controller.iteration.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddIteration {

  @NotBlank
  @Length(max = 30)
  @Schema(description = "迭代名称")
  private String name;

  @NotNull
  @Schema(description = "版本ID")
  private Long versionId;

  @Schema(description = "计划开始时间")
  private LocalDateTime plannedStartTime;

  @Schema(description = "计划结束时间")
  private LocalDateTime plannedEndTime;

  @Length(max = 255)
  @Schema(description = "迭代描述")
  private String description;
}

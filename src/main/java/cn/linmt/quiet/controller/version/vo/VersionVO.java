package cn.linmt.quiet.controller.version.vo;

import cn.linmt.quiet.enums.PlanningStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class VersionVO {

  @NotNull
  @Schema(description = "版本ID")
  private Long id;

  @NotBlank
  @Schema(description = "版本名称")
  private String name;

  @NotNull
  @Schema(description = "项目ID")
  private Long projectId;

  @Schema(description = "父版本ID")
  private Long parentId;

  @NotNull
  @Schema(description = "版本状态")
  private PlanningStatus status;

  @Schema(description = "计划开始时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime plannedStartTime;

  @Schema(description = "计划结束时间")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime plannedEndTime;
}

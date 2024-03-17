package cn.linmt.quiet.controller.version.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddVersion {

  @NotBlank
  @Length(max = 30)
  @Schema(description = "版本名称")
  private String name;

  @NotNull
  @Schema(description = "项目ID")
  private Long projectId;

  @Schema(description = "父级版本ID")
  private Long parentId;

  @Schema(description = "计划开始时间")
  private LocalDateTime plannedStartTime;

  @Schema(description = "计划结束时间")
  private LocalDateTime plannedEndTime;

  @Length(max = 255)
  @Schema(description = "版本描述")
  private String description;
}

package cn.linmt.quiet.controller.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProjectVO {

  @NotNull
  @Schema(description = "项目ID")
  private Long id;

  @NotBlank
  @Length(max = 30)
  @Schema(description = "项目名称")
  private String name;

  @NotNull
  @Schema(description = "模板ID")
  private Long templateId;

  @NotNull
  @Schema(description = "项目组ID")
  private Long projectGroupId;

  @Schema(description = "项目描述")
  private String description;

  @NotNull
  @Schema(description = "创建时间")
  private LocalDateTime gmtCreate;
}

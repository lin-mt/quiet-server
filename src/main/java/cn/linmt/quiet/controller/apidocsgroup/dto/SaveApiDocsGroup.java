package cn.linmt.quiet.controller.apidocsgroup.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SaveApiDocsGroup {

  @NotBlank
  @Length(max = 30)
  @Schema(description = "分组名称")
  private String name;

  @NotNull
  @Schema(description = "项目ID")
  private Long projectId;

  @Schema(description = "父分组ID")
  private Long parentId;

  @Length(max = 255)
  @Schema(description = "项目描述")
  private String description;
}

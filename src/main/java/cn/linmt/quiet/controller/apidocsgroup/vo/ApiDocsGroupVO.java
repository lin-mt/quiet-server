package cn.linmt.quiet.controller.apidocsgroup.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ApiDocsGroupVO {

  @NotNull private Long id;

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
  @Schema(description = "分组描述")
  private String description;

  @Schema(description = "子分组")
  private List<ApiDocsGroupVO> children;
}

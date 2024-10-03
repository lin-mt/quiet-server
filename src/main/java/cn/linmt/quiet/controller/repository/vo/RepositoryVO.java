package cn.linmt.quiet.controller.repository.vo;

import cn.linmt.quiet.controller.DisabledVO;
import cn.linmt.quiet.enums.BuildTool;
import cn.linmt.quiet.modal.RepositoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@Data
@EqualsAndHashCode(callSuper = true)
public class RepositoryVO extends DisabledVO {

  @NotNull
  @Schema(description = "仓库ID")
  private Long id;

  @NotEmpty
  @Length(max = 30)
  @Schema(description = "仓库名称")
  private String name;

  @NotNull
  @Schema(description = "仓库类型")
  private RepositoryType type;

  @NotNull
  @Schema(description = "构建工具")
  private BuildTool buildTool;

  @Schema(description = "访问token")
  private String accessToken;

  @NotEmpty
  @Length(max = 255)
  @Schema(description = "仓库地址")
  private String url;

  @Schema(description = "仓库描述")
  private String description;
}

package cn.linmt.quiet.controller.repository.dto;

import cn.linmt.quiet.enums.BuildTool;
import cn.linmt.quiet.modal.RepositoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddRepository {

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

  @Schema(description = "用户名")
  private String username;

  @Schema(description = "密码")
  private String password;

  @NotEmpty
  @Length(max = 255)
  @Schema(description = "仓库地址")
  private String url;

  @Schema(description = "仓库描述")
  private String description;
}

package cn.linmt.quiet.modal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Repository {

  @Schema(description = "仓库名称")
  private String name;

  @Schema(description = "仓库类型")
  private RepositoryType type;

  @Schema(description = "仓库地址")
  private String address;
}

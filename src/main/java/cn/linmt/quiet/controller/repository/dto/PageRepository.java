package cn.linmt.quiet.controller.repository.dto;

import cn.linmt.quiet.enums.BuildTool;
import cn.linmt.quiet.modal.RepositoryType;
import cn.linmt.quiet.modal.http.PageFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageRepository extends PageFilter {

  @Schema(description = "仓库ID")
  private Long id;

  @Schema(description = "仓库类型")
  private RepositoryType type;

  @Schema(description = "仓库名称")
  private String name;

  @Schema(description = "构建工具")
  private BuildTool buildTool;
}

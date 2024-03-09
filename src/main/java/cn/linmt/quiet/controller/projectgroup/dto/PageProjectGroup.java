package cn.linmt.quiet.controller.projectgroup.dto;

import cn.linmt.quiet.modal.http.PageFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageProjectGroup extends PageFilter {

  @Schema(description = "项目组ID")
  private Long id;

  @Schema(description = "项目组名称")
  private String name;

  @Schema(description = "项目组描述")
  private String description;
}

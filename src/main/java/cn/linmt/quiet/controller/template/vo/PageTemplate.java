package cn.linmt.quiet.controller.template.vo;

import cn.linmt.quiet.modal.http.PageFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageTemplate extends PageFilter {

  @Schema(description = "模板ID")
  private Long id;

  @Schema(description = "模板名称")
  private String name;

  @Schema(description = "模板描述")
  private String description;
}

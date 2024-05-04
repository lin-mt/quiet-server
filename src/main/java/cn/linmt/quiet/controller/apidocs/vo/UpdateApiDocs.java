package cn.linmt.quiet.controller.apidocs.vo;

import cn.linmt.quiet.enums.ApiDocsState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateApiDocs extends SaveApiDocs {

  @Schema(description = "接口文档ID")
  private Long id;

  @NotNull
  @Schema(description = "接口状态")
  private ApiDocsState state;
}

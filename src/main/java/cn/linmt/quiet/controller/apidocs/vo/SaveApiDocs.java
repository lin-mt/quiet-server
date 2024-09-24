package cn.linmt.quiet.controller.apidocs.vo;

import cn.linmt.quiet.enums.HttpMethod;
import cn.linmt.quiet.modal.document.ApiEndpointSpec;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SaveApiDocs {

  @Length(max = 30)
  @Schema(description = "标题")
  private String name;

  @NotNull
  @Schema(description = "请求方法")
  private HttpMethod method;

  @NotBlank
  @Schema(description = "请求路径")
  private String path;

  @Schema(description = "接口规范")
  private ApiEndpointSpec apiEndpointSpec;

  @NotNull
  @Schema(description = "接口分组ID")
  private Long groupId;

  @Length(max = 255)
  @Schema(description = "描述信息")
  private String description;
}

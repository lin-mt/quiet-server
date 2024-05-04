package cn.linmt.quiet.controller.apidocsgroup.vo;

import cn.linmt.quiet.controller.apidocs.vo.ApiDocsVO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiDocsGroupDetail extends ApiDocsGroupVO {

  @Schema(description = "文档详情")
  private List<ApiDocsVO> apiDocs;
}

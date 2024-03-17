package cn.linmt.quiet.controller.version.vo;

import cn.linmt.quiet.controller.iteration.vo.SimpleIteration;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TreeVersionDetail extends SimpleVersion {

  @Schema(description = "子版本信息")
  private List<TreeVersionDetail> children;

  @Schema(description = "迭代信息")
  private List<SimpleIteration> iterations;
}

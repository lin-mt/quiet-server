package cn.linmt.quiet.controller.iteration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IterationDetail extends IterationVO {

  @Schema(description = "需求信息")
  private List<Long> requirements;
}

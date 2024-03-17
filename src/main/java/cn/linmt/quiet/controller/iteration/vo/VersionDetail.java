package cn.linmt.quiet.controller.iteration.vo;

import cn.linmt.quiet.controller.version.vo.VersionVO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VersionDetail extends VersionVO {

  @Schema(description = "实际开始时间")
  private LocalDateTime actualStartTime;

  @Schema(description = "实际结束时间")
  private LocalDateTime actualEndTime;

  @Schema(description = "版本描述")
  private String description;

  @Schema(description = "子版本")
  private List<VersionVO> children;

  @Schema(description = "在该版本的迭代信息")
  private List<IterationVO> iterations;
}

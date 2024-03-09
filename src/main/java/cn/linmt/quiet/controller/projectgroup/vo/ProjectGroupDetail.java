package cn.linmt.quiet.controller.projectgroup.vo;

import cn.linmt.quiet.controller.project.vo.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProjectGroupDetail {
  @NotNull
  @Schema(description = "项目组ID")
  private Long id;

  @Length(max = 30)
  @Schema(description = "项目组名称")
  private String name;

  @Schema(description = "项目组成员")
  private List<Member> members;

  @Length(max = 255)
  @Schema(description = "项目组描述")
  private String description;

  @NotNull
  @Schema(description = "创建时间")
  private LocalDateTime gmtCreate;
}

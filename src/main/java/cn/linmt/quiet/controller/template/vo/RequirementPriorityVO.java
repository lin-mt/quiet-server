package cn.linmt.quiet.controller.template.vo;

import cn.linmt.quiet.controller.DisabledVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@Data
@EqualsAndHashCode(callSuper = true)
public class RequirementPriorityVO extends DisabledVO {

  @NotNull
  @Schema(description = "优先级ID")
  private Long id;

  @NotBlank
  @Length(max = 30)
  @Schema(description = "优先级名称")
  private String name;

  @NotBlank
  @Schema(description = "卡片颜色")
  private String color;

  @Schema(description = "优先级描述")
  private String description;
}

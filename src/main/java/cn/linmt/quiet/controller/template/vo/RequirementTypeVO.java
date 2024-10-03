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
public class RequirementTypeVO extends DisabledVO {

  @NotNull
  @Schema(description = "需求类型ID")
  private Long id;

  @NotBlank
  @Length(max = 30)
  @Schema(description = "需求类型名称")
  private String name;

  @Schema(description = "需求类型描述")
  private String description;
}

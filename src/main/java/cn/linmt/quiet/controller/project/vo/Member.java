package cn.linmt.quiet.controller.project.vo;

import cn.linmt.quiet.controller.DisabledVO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Member extends DisabledVO {

  @NotNull
  @Schema(description = "用户ID")
  private Long id;

  @NotBlank
  @Schema(description = "用户名")
  private String username;
}

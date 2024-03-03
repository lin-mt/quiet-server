package cn.linmt.quiet.controller.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SimpleUser {

  @NotNull
  @Schema(description = "用户ID")
  private Long id;

  @NotBlank
  @Schema(description = "用户名")
  private String username;
}

package cn.linmt.quiet.controller.role.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

@Data
@Schema(description = "角色信息")
public class RoleInfo implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  @NotNull
  @Schema(description = "角色ID")
  private Long id;

  @NotBlank
  @Schema(description = "角色名称")
  private String name;

  @NotBlank
  @Schema(description = "角色值")
  private String value;
}

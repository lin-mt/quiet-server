package cn.linmt.quiet.controller.role.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Schema(description = "角色信息")
public class RoleVO {

  @NotNull
  @Schema(description = "角色ID")
  private Long id;

  @Schema(description = "父角色ID")
  private Long parentId;

  @Schema(description = "序号")
  private int ordinal;

  @NotBlank
  @Schema(description = "角色名称")
  private String name;

  @NotBlank
  @Schema(description = "角色值")
  private String value;

  @NotBlank
  @Schema(description = "角色编码")
  private String code;

  @Schema(description = "父角色编码")
  private String parentCode;

  @NotNull
  @Schema(description = "创建时间")
  private LocalDateTime gmtCreate;
}

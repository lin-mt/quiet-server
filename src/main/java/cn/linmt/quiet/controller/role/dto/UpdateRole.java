package cn.linmt.quiet.controller.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

public record UpdateRole(
    @Schema(description = "主键ID") @NotNull Long id,
    @Schema(description = "排序值") @Min(0) int ordinal,
    @Schema(description = "父角色ID") Long parentId,
    @Schema(description = "角色名称") @NotBlank @Length(max = 16) String name,
    @Schema(description = "角色值") @NotBlank @Length(max = 32) String value,
    @Schema(description = "角色编码") @NotBlank @Length(max = 32) String code)
    implements Serializable {}

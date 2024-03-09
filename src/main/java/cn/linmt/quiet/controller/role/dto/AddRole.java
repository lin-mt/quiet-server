package cn.linmt.quiet.controller.role.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

public record AddRole(
    @Schema(description = "排序值") @Min(0) int ordinal,
    @Schema(description = "父角色ID") Long parentId,
    @Schema(description = "角色名称") @NotBlank @Length(max = 30) String name,
    @Schema(description = "角色值") @NotBlank @Length(max = 32) String value,
    @Schema(description = "角色编码") @NotBlank @Length(max = 32) String code)
    implements Serializable {}

package cn.linmt.quiet.controller.permission.dto;

import cn.linmt.quiet.enums.HttpMethod;
import cn.linmt.quiet.enums.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import org.hibernate.validator.constraints.Length;

public record AddPermission(
    @Schema(description = "排序值") @Min(0) int ordinal,
    @Schema(description = "父ID") Long parentId,
    @Schema(description = "权限名称") @NotBlank @Length(max = 30) String name,
    @Schema(description = "权限类型") @NotNull PermissionType type,
    @Schema(description = "路径") @NotBlank @Length(max = 255) String path,
    @Schema(description = "值") @NotBlank @Length(max = 255) String value,
    @Schema(description = "请求地址") @Length(max = 255) String httpUrl,
    @Schema(description = "请求方法") HttpMethod httpMethod,
    @Schema(description = "备注信息") @Length(max = 255) String remark)
    implements Serializable {}

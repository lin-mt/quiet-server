package cn.linmt.quiet.controller.project.dto;

import cn.linmt.quiet.enums.BuildTool;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record AddProject(
    @Schema(description = "项目名称") @NotBlank @Length(max = 30) String name,
    @Schema(description = "模板ID") @NotNull Long templateId,
    @Schema(description = "项目组ID") @NotNull Long projectGroupId,
    @Schema(description = "构建工具") @NotNull BuildTool buildTool,
    @Schema(description = "git地址") @NotBlank @Length(max = 255) String gitAddress,
    @Schema(description = "项目描述") @Length(max = 255) String description) {}

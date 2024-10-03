package cn.linmt.quiet.controller.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import org.hibernate.validator.constraints.Length;

public record AddProject(
    @Schema(description = "项目名称") @NotBlank @Length(max = 30) String name,
    @Schema(description = "模板ID") @NotNull Long templateId,
    @Schema(description = "项目组ID") @NotNull Long projectGroupId,
    @Schema(description = "代码仓库") Set<Long> repositories,
    @Schema(description = "项目描述") @Length(max = 255) String description) {}

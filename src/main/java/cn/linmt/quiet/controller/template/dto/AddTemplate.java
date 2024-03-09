package cn.linmt.quiet.controller.template.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddTemplate extends TemplateInfo<AddTaskStep, AddRequirementPriority> {}

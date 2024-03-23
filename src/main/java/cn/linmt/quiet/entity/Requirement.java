package cn.linmt.quiet.entity;

import cn.linmt.quiet.enums.RequirementStatus;
import cn.linmt.quiet.modal.jpa.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
@Table(name = "requirement")
public class Requirement extends BaseEntity {

  @NotBlank
  @Comment("需求标题")
  @Length(max = 30)
  @Column(name = "title", nullable = false, length = 30)
  private String title;

  @NotNull
  @Comment("需求类型")
  @Column(name = "type_id", nullable = false)
  private Long typeId;

  @NotNull
  @Comment("需求状态")
  @Column(name = "status", nullable = false)
  private RequirementStatus status;

  @NotNull
  @Comment("优先级ID")
  @Column(name = "priority_id", nullable = false)
  private Long priorityId;

  @NotNull
  @Comment("项目ID")
  @Column(name = "project_id", nullable = false)
  private Long projectId;

  @Comment("迭代ID")
  @Column(name = "iteration_id", nullable = false)
  private Long iterationId;

  @NotNull
  @Comment("报告人")
  @Column(name = "reporter_id", nullable = false)
  private Long reporterId;

  @NotNull
  @Comment("处理人")
  @Column(name = "handler_id", nullable = false)
  private Long handlerId;

  @Length(max = 255)
  @Comment("描述")
  @Column(name = "description")
  private String description;
}

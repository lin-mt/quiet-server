package cn.linmt.quiet.entity;

import cn.linmt.quiet.modal.jpa.base.SortableEntity;
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
@Table(name = "task")
public class Task extends SortableEntity {

  @NotBlank
  @Comment("任务标题")
  @Column(name = "title", nullable = false, length = 30)
  private String title;

  @NotNull
  @Comment("任务类型")
  @Column(name = "type_id", nullable = false)
  private Long typeId;

  @NotNull
  @Comment("当前所在的任务步骤ID")
  @Column(name = "task_step_id", nullable = false)
  private Long taskStepId;

  @NotNull
  @Comment("需求ID")
  @Column(name = "requirement_id", nullable = false)
  private Long requirementId;

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

package cn.linmt.quiet.entity;

import cn.linmt.quiet.modal.jpa.base.SortableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "task_step")
public class TaskStep extends SortableEntity {

  @Comment("步骤名称")
  @Column(nullable = false, length = 30)
  private String name;

  @Comment("模板ID")
  @Column(nullable = false)
  private Long templateId;

  @Comment("步骤描述")
  private String description;
}

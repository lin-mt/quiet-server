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
@Table(name = "task_type")
public class TaskType extends SortableEntity {

  @Comment("任务类型名称")
  @Column(nullable = false, length = 30)
  private String name;

  @Comment("模板ID")
  @Column(nullable = false)
  private Long templateId;

  @Comment("是否为后端接口任务")
  private Boolean backendApi;

  @Comment("任务类型描述")
  private String description;
}

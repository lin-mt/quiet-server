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
@Table(name = "requirement_priority")
public class RequirementPriority extends SortableEntity {

  @Comment("优先级名称")
  @Column(nullable = false, length = 30)
  private String name;

  @Comment("卡片颜色")
  @Column(nullable = false, length = 16)
  private String color;

  @Comment("模板ID")
  @Column(nullable = false)
  private Long templateId;

  @Comment("优先级描述")
  private String description;
}

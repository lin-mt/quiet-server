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
  @Column(name = "rp_name", nullable = false, length = 30)
  private String name;

  @Comment("卡片颜色")
  @Column(name = "color", nullable = false, length = 16)
  private String color;

  @Comment("模板ID")
  @Column(name = "template_id", nullable = false)
  private Long templateId;

  @Comment("优先级描述")
  @Column(name = "description")
  private String description;
}

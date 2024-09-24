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
@Table(name = "requirement_type")
public class RequirementType extends SortableEntity {

  @Comment("需求类型名称")
  @Column(nullable = false, length = 30)
  private String name;

  @Comment("模板ID")
  @Column(nullable = false)
  private Long templateId;

  @Comment("需求类型描述")
  private String description;
}
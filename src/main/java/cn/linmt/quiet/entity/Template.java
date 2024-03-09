package cn.linmt.quiet.entity;

import cn.linmt.quiet.modal.jpa.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "template")
public class Template extends BaseEntity {

  @Comment("模板名称")
  @Column(name = "t_name", nullable = false, length = 30)
  private String name;

  @Comment("模板描述")
  @Column(name = "description")
  private String description;
}

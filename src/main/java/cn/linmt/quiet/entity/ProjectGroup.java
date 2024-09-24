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
@Table(name = "project_group")
public class ProjectGroup extends BaseEntity {

  @Comment("项目组名称")
  @Column(length = 30, nullable = false)
  private String name;

  @Comment("项目组描述")
  private String description;
}

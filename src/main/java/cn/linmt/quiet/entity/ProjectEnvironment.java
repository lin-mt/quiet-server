package cn.linmt.quiet.entity;

import cn.linmt.quiet.modal.jpa.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "project_environment")
public class ProjectEnvironment extends BaseEntity {

  @Comment("项目ID")
  @Column(nullable = false)
  private Long projectId;

  @NotEmpty
  @Comment("环境名称")
  @Column(nullable = false)
  private String name;
}

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
@Table(name = "project_user")
public class ProjectUser extends BaseEntity {

  @Comment("项目ID")
  @Column(name = "project_id", nullable = false)
  private Long projectId;

  @Comment("用户ID")
  @Column(name = "user_id", nullable = false)
  private Long userId;
}

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
@Table(name = "project_group_user")
public class ProjectGroupUser extends BaseEntity {

  @Comment("项目组ID")
  @Column(name = "project_group_id", nullable = false)
  private Long projectGroupId;

  @Comment("用户ID")
  @Column(name = "user_id", nullable = false)
  private Long userId;
}

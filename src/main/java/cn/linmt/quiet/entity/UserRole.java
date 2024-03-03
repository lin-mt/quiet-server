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
@Table(name = "user_role")
public class UserRole extends BaseEntity {

  @Comment("用户ID")
  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Comment("角色ID")
  @Column(name = "role_id", nullable = false)
  private Long roleId;

}

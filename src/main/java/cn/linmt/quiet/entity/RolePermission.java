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
@Table(name = "role_permission")
public class RolePermission extends BaseEntity {

  @Comment("角色ID")
  @Column(nullable = false)
  private Long roleId;

  @Comment("权限ID")
  @Column(nullable = false)
  private Long permissionId;
}

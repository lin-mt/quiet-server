package cn.linmt.quiet.entity;

import cn.linmt.quiet.enums.Enabled;
import cn.linmt.quiet.enums.Expired;
import cn.linmt.quiet.enums.Locked;
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
@Table(name = "user_info")
public class User extends BaseEntity {

  @Comment("用户名")
  @Column(nullable = false, length = 30)
  private String username;

  @Comment("密码")
  @Column(nullable = false, length = 60)
  private String password;

  @Comment("账号未过期")
  @Column(nullable = false)
  private Expired accountExpired;

  @Comment("账号未锁定")
  @Column(nullable = false)
  private Locked accountLocked;

  @Comment("密码未过期")
  @Column(nullable = false)
  private Expired credentialsExpired;

  @Comment("账号启用")
  @Column(nullable = false)
  private Enabled enabled;
}

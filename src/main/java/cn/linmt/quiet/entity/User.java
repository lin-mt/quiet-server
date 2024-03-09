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
@Table(name = "user")
public class User extends BaseEntity {

  @Comment("用户名")
  @Column(name = "username", nullable = false, length = 30)
  private String username;

  @Comment("密码")
  @Column(name = "secret_code", nullable = false, length = 60)
  private String password;

  @Comment("账号未过期")
  @Column(name = "account_expired", nullable = false)
  private Expired accountExpired;

  @Comment("账号未锁定")
  @Column(name = "account_locked", nullable = false)
  private Locked accountLocked;

  @Comment("密码未过期")
  @Column(name = "credentials_expired", nullable = false)
  private Expired credentialsExpired;

  @Comment("账号启用")
  @Column(name = "enabled", nullable = false)
  private Enabled enabled;
}

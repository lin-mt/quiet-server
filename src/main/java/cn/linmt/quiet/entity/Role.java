package cn.linmt.quiet.entity;

import cn.linmt.quiet.modal.jpa.base.ParentAndSortableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends ParentAndSortableEntity {

  @Comment("角色名称")
  @Column(name = "name", nullable = false, length = 30)
  private String name;

  @Comment("角色值")
  @Column(name = "value", nullable = false, length = 32)
  private String value;

  @Comment("角色编码")
  @Column(name = "code", nullable = false, length = 32)
  private String code;
}

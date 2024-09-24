package cn.linmt.quiet.entity;

import cn.linmt.quiet.enums.HttpMethod;
import cn.linmt.quiet.enums.PermissionType;
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
@Table(name = "permission")
public class Permission extends ParentAndSortableEntity {

  @Comment("权限名称")
  @Column(nullable = false, length = 30)
  private String name;

  @Comment("权限类型")
  @Column(nullable = false)
  private PermissionType type;

  @Comment("权限值")
  @Column(nullable = false)
  private String value;

  @Comment("路径")
  private String path;

  @Comment("请求URL")
  private String httpUrl;

  @Comment("请求方法")
  private HttpMethod httpMethod;

  @Comment("权限描述")
  private String description;
}

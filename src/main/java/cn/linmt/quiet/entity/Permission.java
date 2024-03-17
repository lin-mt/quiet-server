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
  @Column(name = "name", nullable = false, length = 30)
  private String name;

  @Comment("权限类型")
  @Column(name = "type", nullable = false)
  private PermissionType type;

  @Comment("权限值")
  @Column(name = "value", nullable = false)
  private String value;

  @Comment("路径")
  @Column(name = "path")
  private String path;

  @Comment("请求URL")
  @Column(name = "http_url")
  private String httpUrl;

  @Comment("请求方法")
  @Column(name = "http_method")
  private HttpMethod httpMethod;

  @Comment("备注")
  @Column(name = "remark")
  private String remark;
}

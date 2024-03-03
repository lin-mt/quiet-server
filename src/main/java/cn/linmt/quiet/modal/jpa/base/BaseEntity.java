package cn.linmt.quiet.modal.jpa.base;

import cn.linmt.quiet.util.IdGenerator;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 实体类的公共属性.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@SoftDelete
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public class BaseEntity implements Serializable {

  @Id
  @Comment("主键ID")
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdGenerator")
  @GenericGenerator(name = "IdGenerator", type = IdGenerator.class)
  private Long id;

  @CreatedBy
  @Comment("创建者")
  @Column(name = "creator", updatable = false)
  private Long creator;

  @LastModifiedBy
  @Comment("更新者")
  @Column(name = "updater", insertable = false)
  private Long updater;

  @CreatedDate
  @Comment("创建时间")
  @Column(name = "gmt_create", updatable = false)
  private LocalDateTime gmtCreate;

  @LastModifiedDate
  @Comment("更新时间")
  @Column(name = "gmt_update", insertable = false)
  private LocalDateTime gmtUpdate;
}

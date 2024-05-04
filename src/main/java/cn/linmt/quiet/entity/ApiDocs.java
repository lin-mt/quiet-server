package cn.linmt.quiet.entity;

import cn.linmt.quiet.enums.ApiDocsState;
import cn.linmt.quiet.enums.HttpMethod;
import cn.linmt.quiet.modal.ApiDetail;
import cn.linmt.quiet.modal.jpa.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
@Table(name = "api_docs")
public class ApiDocs extends BaseEntity {

  @Length(max = 30)
  @Comment("名称")
  @Column(name = "name", length = 30)
  private String name;

  @NotNull
  @Comment("请求方法")
  @Column(name = "method")
  private HttpMethod method;

  @NotBlank
  @Comment("请求路径")
  @Column(name = "path")
  private String path;

  @NotNull
  @Comment("接口状态")
  @Column(name = "state")
  private ApiDocsState state;

  @Comment("接口详细信息")
  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "detail")
  private ApiDetail detail;

  @NotNull
  @Comment("接口分组ID")
  @Column(name = "group_id", nullable = false)
  private Long groupId;

  @Length(max = 255)
  @Comment("描述信息")
  @Column(name = "description")
  private String description;
}

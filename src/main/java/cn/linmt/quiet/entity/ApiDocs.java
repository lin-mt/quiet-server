package cn.linmt.quiet.entity;

import cn.linmt.quiet.enums.ApiDocsState;
import cn.linmt.quiet.enums.HttpMethod;
import cn.linmt.quiet.modal.document.ApiEndpointSpec;
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
  @Column(length = 30)
  private String name;

  @NotNull
  @Comment("请求方法")
  private HttpMethod method;

  @NotBlank
  @Comment("请求路径")
  private String uri;

  @NotNull
  @Comment("接口状态")
  private ApiDocsState state;

  @Comment("接口规范")
  @JdbcTypeCode(SqlTypes.JSON)
  private ApiEndpointSpec apiEndpointSpec;

  @NotNull
  @Comment("接口分组ID")
  @Column(nullable = false)
  private Long groupId;

  @Length(max = 255)
  @Comment("描述信息")
  private String description;
}

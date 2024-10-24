package cn.linmt.quiet.entity;

import cn.linmt.quiet.enums.BuildTool;
import cn.linmt.quiet.enums.RepositoryType;
import cn.linmt.quiet.modal.jpa.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
@Table(name = "repository")
public class Repository extends BaseEntity {

  @NotEmpty
  @Length(max = 30)
  @Comment("仓库名称")
  private String name;

  @NotNull
  @Comment("仓库类型")
  private RepositoryType type;

  @Comment("构建工具")
  @Column(nullable = false)
  private BuildTool buildTool;

  @Comment("访问token")
  private String accessToken;

  @Comment("用户名")
  private String username;

  @Comment("密码")
  private String password;

  @Comment("初始化向量，用于加密的随机数")
  private String iv;

  @NotEmpty
  @Length(max = 255)
  @Comment("仓库地址")
  private String url;

  @Comment("仓库描述")
  private String description;
}

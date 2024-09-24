package cn.linmt.quiet.entity;

import cn.linmt.quiet.modal.jpa.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "project_repository")
public class ProjectRepository extends BaseEntity {

  @NotNull
  @Comment("项目ID")
  @Column(nullable = false)
  private Long projectId;

  @NotNull
  @Comment("仓库ID")
  @Column(nullable = false)
  private Long repositoryId;

  @Comment("是否自动创建分支")
  private boolean autoCreateBranch;

  @Comment("是否自动创建PullRequest")
  private boolean autoCreatePullRequest;
}

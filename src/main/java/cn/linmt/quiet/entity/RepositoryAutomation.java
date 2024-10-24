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
@Table(name = "repository_automation")
public class RepositoryAutomation extends BaseEntity {

  @NotNull
  @Comment("代码仓库ID")
  @Column(nullable = false)
  private Long repositoryId;

  @NotNull
  @Comment("项目ID")
  @Column(nullable = false)
  private Long projectId;

  @Comment("任务ID")
  private Long taskId;

  @Comment("需求ID")
  private Long requirementId;

  @Comment("分支名称")
  private String branchName;

  @Comment("PR ID")
  private Integer pullRequestId;

  @Comment("PR 名称")
  private String pullRequestName;

  @Comment("issue ID")
  private Integer issueId;

  @Comment("issue 名称")
  private String issueName;
}

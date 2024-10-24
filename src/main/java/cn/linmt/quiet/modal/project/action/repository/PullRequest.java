package cn.linmt.quiet.modal.project.action.repository;

import lombok.Data;

@Data
public class PullRequest {

  /** 仓库ID */
  private Long repositoryId;

  /** 项目仓库的 PR Id */
  private Integer id;

  /** PR 名称 */
  private String name;

  /** PR 描述信息 */
  private String description;
}

package cn.linmt.quiet.modal.project.action.repository;

import lombok.Data;

@Data
public class Issue {

  /** 仓库ID */
  private Long repositoryId;

  /** 项目仓库的issueId */
  private Integer id;

  /** issue 名称 */
  private String name;

  /** issue 描述信息 */
  private String description;
}

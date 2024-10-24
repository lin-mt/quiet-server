package cn.linmt.quiet.modal.project.action.repository;

import lombok.Data;

@Data
public class Branch {

  /** 仓库ID */
  private Long repositoryId;

  /** 分支名称 */
  private String name;
}

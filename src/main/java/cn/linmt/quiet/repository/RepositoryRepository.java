package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.Repository;
import cn.linmt.quiet.framework.QuietRepository;

public interface RepositoryRepository extends QuietRepository<Repository> {

  /**
   * 根据仓库名称查询仓库
   *
   * @param name 仓库名称
   * @return 仓库信息
   */
  Repository findByName(String name);
}

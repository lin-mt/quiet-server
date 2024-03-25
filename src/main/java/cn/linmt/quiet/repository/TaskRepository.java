package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.Task;
import cn.linmt.quiet.framework.QuietRepository;

public interface TaskRepository extends QuietRepository<Task> {
  void deleteByRequirementId(Long requirementId);
}

package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.Task;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.Collection;
import java.util.List;

public interface TaskRepository extends QuietRepository<Task> {
  void deleteByRequirementId(Long requirementId);

  List<Task> findByRequirementIdIn(Collection<Long> requirementIds);

  Task findByRequirementIdAndTypeIdAndTitleIgnoreCase(
      Long requirementId, Long typeId, String title);
}

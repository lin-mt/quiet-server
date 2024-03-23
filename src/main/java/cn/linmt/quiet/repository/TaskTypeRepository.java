package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.TaskType;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;

public interface TaskTypeRepository extends QuietRepository<TaskType> {
  List<TaskType> findByTemplateId(Long templateId);

  void deleteByTemplateId(Long templateId);
}

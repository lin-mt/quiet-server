package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.TaskStep;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;

public interface TaskStepRepository extends QuietRepository<TaskStep> {
  List<TaskStep> findByTemplateId(Long templateId);

  void deleteByTemplateId(Long templateId);
}

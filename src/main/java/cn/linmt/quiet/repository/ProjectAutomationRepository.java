package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.ProjectAutomation;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;

public interface ProjectAutomationRepository extends QuietRepository<ProjectAutomation> {
  void deleteAllByProjectId(Long projectId);

  List<ProjectAutomation> searchAllByProjectId(Long projectId);
}

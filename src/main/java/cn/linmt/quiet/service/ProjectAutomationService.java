package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.ProjectAutomation;
import cn.linmt.quiet.repository.ProjectAutomationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectAutomationService {

  private final ProjectAutomationRepository repository;

  @Transactional(rollbackFor = Exception.class)
  public void saveProjectAutomations(Long projectId, List<ProjectAutomation> automations) {
    repository.deleteAllByProjectId(projectId);
    if (CollectionUtils.isEmpty(automations)) {
      return;
    }
    automations.forEach(automation -> automation.setProjectId(projectId));
    repository.saveAll(automations);
  }

  public List<ProjectAutomation> listByProjectId(Long projectId) {
    return repository.searchAllByProjectId(projectId);
  }
}

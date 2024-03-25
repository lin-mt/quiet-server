package cn.linmt.quiet.manager;

import cn.linmt.quiet.service.RequirementService;
import cn.linmt.quiet.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequirementManager {

  private final TaskService taskService;
  private final RequirementService requirementService;

  @Transactional(rollbackFor = Exception.class)
  public void deleteById(Long id) {
    taskService.deleteByRequirementId(id);
    requirementService.deleteById(id);
  }
}

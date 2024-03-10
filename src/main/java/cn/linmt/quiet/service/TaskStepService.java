package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.TaskStep;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.repository.TaskStepRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskStepService {

  private final TaskStepRepository repository;

  @Transactional(rollbackFor = Exception.class)
  public void saveAll(Long templateId, List<TaskStep> taskSteps) {
    Set<String> names = new HashSet<>();
    Set<Long> ids = new HashSet<>();
    for (int i = 0; i < taskSteps.size(); i++) {
      TaskStep taskStep = taskSteps.get(i);
      Long id = taskStep.getId();
      if (!names.add(taskStep.getName())) {
        Result.TASK_STEP_NAME_REPEAT.thr();
      }
      if (id != null) {
        ids.add(id);
      }
      taskStep.setTemplateId(templateId);
      taskStep.setOrdinal(i);
    }
    List<TaskStep> existSteps = repository.findByTemplateId(templateId);
    if (CollectionUtils.isNotEmpty(existSteps)) {
      List<TaskStep> removed =
          existSteps.stream().filter(step -> !ids.contains(step.getId())).toList();
      if (CollectionUtils.isNotEmpty(removed)) {
        repository.deleteAll(removed);
        // TODO 将删除的任务步骤下的任务移动到前一个步骤，前一个步骤不存在则移动到下一个步骤
      }
    }
    repository.saveAll(taskSteps);
  }

  public List<TaskStep> listByTemplateId(Long id) {
    return repository.findByTemplateId(id).stream()
        .sorted()
        .toList();
  }

  public void deleteByTemplateId(Long templateId) {
    repository.deleteByTemplateId(templateId);
  }
}

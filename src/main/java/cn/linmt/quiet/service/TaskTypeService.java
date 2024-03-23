package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.TaskType;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.repository.TaskTypeRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskTypeService {

  private final TaskTypeRepository repository;

  @Transactional(rollbackFor = Exception.class)
  public void saveAll(Long templateId, List<TaskType> taskTypes) {
    Set<String> names = new HashSet<>();
    Set<Long> ids = new HashSet<>();
    for (int i = 0; i < taskTypes.size(); i++) {
      TaskType taskType = taskTypes.get(i);
      Long id = taskType.getId();
      if (!names.add(taskType.getName())) {
        Result.TASK_TYPE_NAME_REPEAT.thr();
      }
      if (id != null) {
        ids.add(id);
      }
      taskType.setTemplateId(templateId);
      taskType.setOrdinal(i);
    }
    List<TaskType> existTypes = repository.findByTemplateId(templateId);
    if (CollectionUtils.isNotEmpty(existTypes)) {
      List<TaskType> removed =
          existTypes.stream().filter(step -> !ids.contains(step.getId())).toList();
      if (CollectionUtils.isNotEmpty(removed)) {
        repository.deleteAll(removed);
        throw new IllegalStateException("该任务类型下包含任务信息，无法删除");
        // TODO 将删除的任务类型下的任务移动到指定类型
      }
    }
    repository.saveAll(taskTypes);
  }

  public List<TaskType> listByTemplateId(Long id) {
    return repository.findByTemplateId(id).stream()
        .sorted()
        .toList();
  }

  public void deleteByTemplateId(Long templateId) {
    repository.deleteByTemplateId(templateId);
  }
}

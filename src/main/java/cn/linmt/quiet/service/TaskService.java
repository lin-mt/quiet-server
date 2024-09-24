package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.Task;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.repository.TaskRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository repository;

  public void deleteByRequirementId(Long id) {
    repository.deleteByRequirementId(id);
  }

  public List<Task> listByRequirementIds(Set<Long> requirementIds) {
    if (CollectionUtils.isEmpty(requirementIds)) {
      return List.of();
    }
    return repository.findByRequirementIdIn(requirementIds);
  }

  public Task save(Task task) {
    Task exist =
        repository.findByRequirementIdAndTypeIdAndTitleIgnoreCase(
            task.getRequirementId(), task.getTypeId(), task.getTitle());
    if (exist != null && !exist.getId().equals(task.getId())) {
      throw new BizException(113001);
    }
    return repository.saveAndFlush(task);
  }

  public void delete(Long id) {
    Task task = getById(id);
    repository.delete(task);
  }

  public void moveTask(Long id, Long taskStepId) {
    Task task = getById(id);
    task.setTaskStepId(taskStepId);
    repository.saveAndFlush(task);
  }

  private Task getById(Long id) {
    return repository.findById(id).orElseThrow(() -> new BizException(113000));
  }
}

package cn.linmt.quiet.manager;

import cn.linmt.quiet.entity.Project;
import cn.linmt.quiet.entity.Task;
import cn.linmt.quiet.entity.TaskStep;
import cn.linmt.quiet.service.ProjectService;
import cn.linmt.quiet.service.TaskService;
import cn.linmt.quiet.service.TaskStepService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskManager {

  private final TaskService taskService;
  private final TaskStepService taskStepService;
  private final ProjectService projectService;

  public Task save(Task task) {
    if (task.getTaskStepId() == null) {
      Project project = projectService.getById(task.getProjectId());
      List<TaskStep> taskSteps = taskStepService.listByTemplateId(project.getTemplateId());
      task.setTaskStepId(taskSteps.get(0).getId());
    }
    return taskService.save(task);
  }
}

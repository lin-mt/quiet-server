package cn.linmt.quiet.controller.task;

import cn.linmt.quiet.controller.task.dto.AddTask;
import cn.linmt.quiet.controller.task.dto.UpdateTask;
import cn.linmt.quiet.entity.Task;
import cn.linmt.quiet.manager.TaskManager;
import cn.linmt.quiet.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

  private final TaskService taskService;
  private final TaskManager taskManager;

  @PostMapping
  @Operation(summary = "新建任务")
  public Long addTask(@RequestBody AddTask addTask) {
    Task task = new Task();
    BeanUtils.copyProperties(addTask, task);
    return taskManager.save(task).getId();
  }

  @PutMapping
  @Operation(summary = "更新任务")
  public Long updateTask(@RequestBody UpdateTask updateTask) {
    Task task = new Task();
    BeanUtils.copyProperties(updateTask, task);
    return taskService.save(task).getId();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除任务")
  public void deleteTask(@PathVariable Long id) {
    taskService.delete(id);
  }

  @PutMapping("/moveTask/{id}")
  @Operation(summary = "移动任务")
  public void moveTask(@PathVariable Long id, @RequestParam Long taskStepId) {
    taskService.moveTask(id, taskStepId);
  }
}

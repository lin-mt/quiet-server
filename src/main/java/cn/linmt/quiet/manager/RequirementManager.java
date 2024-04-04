package cn.linmt.quiet.manager;

import cn.linmt.quiet.controller.requirement.dto.ListRequirementTask;
import cn.linmt.quiet.controller.requirement.vo.RequirementTask;
import cn.linmt.quiet.controller.task.vo.TaskVO;
import cn.linmt.quiet.entity.QRequirement;
import cn.linmt.quiet.entity.QTask;
import cn.linmt.quiet.entity.Requirement;
import cn.linmt.quiet.entity.Task;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.service.RequirementService;
import cn.linmt.quiet.service.TaskService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequirementManager {

  private final TaskService taskService;
  private final JPAQueryFactory queryFactory;
  private final RequirementService requirementService;

  @Transactional(rollbackFor = Exception.class)
  public void deleteById(Long id) {
    taskService.deleteByRequirementId(id);
    requirementService.deleteById(id);
  }

  public List<RequirementTask> requirementTask(ListRequirementTask filter) {
    QRequirement requirement = QRequirement.requirement;
    QTask task = QTask.task;
    String title = filter.getTitle();
    Long handlerId = filter.getHandlerId();
    BooleanBuilder predicate =
        Where.builder()
            .and(requirement.iterationId.eq(filter.getIterationId()))
            .isIdEq(filter.getPriorityId(), requirement.priorityId)
            .with(
                where -> {
                  if (StringUtils.isNotBlank(title)) {
                    where.and(requirement.title.contains(title).or(task.title.contains(title)));
                  }
                  if (handlerId != null && handlerId > 0) {
                    where.and(requirement.handlerId.eq(handlerId).or(task.handlerId.eq(handlerId)));
                  }
                })
            .getPredicate();
    List<Requirement> requirements =
        queryFactory
            .selectFrom(requirement)
            .leftJoin(task)
            .on(requirement.id.eq(task.requirementId))
            .where(predicate)
            .fetch();
    Set<Long> requirementIds = new HashSet<>();
    Map<Long, RequirementTask> id2info = new HashMap<>();
    for (Requirement temp : requirements) {
      RequirementTask requirementTask = new RequirementTask();
      BeanUtils.copyProperties(temp, requirementTask);
      requirementIds.add(temp.getId());
      id2info.put(temp.getId(), requirementTask);
    }
    List<Task> tasks = taskService.listByRequirementIds(requirementIds);
    for (Task temp : tasks) {
      RequirementTask requirementTask = id2info.get(temp.getRequirementId());
      if (StringUtils.isNotBlank(title)) {
        if (!requirementTask.getTitle().contains(title) && !temp.getTitle().contains(title)) {
          continue;
        }
      }
      if (handlerId != null && handlerId > 0) {
        if (!requirementTask.getHandlerId().equals(handlerId)
            && !temp.getHandlerId().equals(handlerId)) {
          continue;
        }
      }
      TaskVO vo = new TaskVO();
      BeanUtils.copyProperties(temp, vo);
      Map<Long, List<TaskVO>> stepId2tasks = requirementTask.getTasks();
      if (stepId2tasks == null) {
        stepId2tasks = new HashMap<>();
        requirementTask.setTasks(stepId2tasks);
      }
      stepId2tasks.computeIfAbsent(temp.getTaskStepId(), (k) -> new ArrayList<>()).add(vo);
    }
    return new ArrayList<>(id2info.values());
  }
}

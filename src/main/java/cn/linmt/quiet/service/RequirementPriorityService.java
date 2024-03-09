package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.RequirementPriority;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.repository.RequirementPriorityRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequirementPriorityService {

  private final RequirementPriorityRepository repository;

  @Transactional(rollbackFor = Exception.class)
  public void saveAll(Long templateId, List<RequirementPriority> priorities) {
    Set<String> names = new HashSet<>();
    Set<Long> ids = new HashSet<>();
    for (int i = 0; i < priorities.size(); i++) {
      RequirementPriority priority = priorities.get(i);
      Long id = priority.getId();
      if (!names.add(priority.getName())) {
        Result.REQ_PRIORITY_NAME_REPEAT.thr();
      }
      if (id != null) {
        ids.add(id);
      }
      priority.setTemplateId(templateId);
      priority.setOrdinal(i);
    }
    List<RequirementPriority> existSteps = repository.findByTemplateId(templateId);
    if (CollectionUtils.isNotEmpty(existSteps)) {
      List<RequirementPriority> removed =
          existSteps.stream().filter(step -> !ids.contains(step.getId())).toList();
      if (CollectionUtils.isNotEmpty(removed)) {
        repository.deleteAll(removed);
        // TODO 将删除的优先级下的需求移动到上一个优先级，上一个优先级不存在则移动到下一个优先级
      }
    }
    repository.saveAll(priorities);
  }

  public List<RequirementPriority> listByTemplateId(Long id) {
    return repository.findByTemplateId(id);
  }

  public void deleteByTemplateId(Long templateId) {
    repository.deleteByTemplateId(templateId);
  }
}

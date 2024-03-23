package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.RequirementType;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.repository.RequirementTypeRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequirementTypeService {

  private final RequirementTypeRepository repository;

  @Transactional(rollbackFor = Exception.class)
  public void saveAll(Long templateId, List<RequirementType> requirementTypes) {
    Set<String> names = new HashSet<>();
    Set<Long> ids = new HashSet<>();
    for (int i = 0; i < requirementTypes.size(); i++) {
      RequirementType priority = requirementTypes.get(i);
      Long id = priority.getId();
      if (!names.add(priority.getName())) {
        Result.REQ_TYPE_NAME_REPEAT.thr();
      }
      if (id != null) {
        ids.add(id);
      }
      priority.setTemplateId(templateId);
      priority.setOrdinal(i);
    }
    List<RequirementType> existSteps = repository.findByTemplateId(templateId);
    if (CollectionUtils.isNotEmpty(existSteps)) {
      List<RequirementType> removed =
          existSteps.stream().filter(step -> !ids.contains(step.getId())).toList();
      if (CollectionUtils.isNotEmpty(removed)) {
        repository.deleteAll(removed);
        throw new IllegalStateException("该需求类型下包含需求信息，无法删除");
        // TODO 将删除的类型移动到指定需求类型下
      }
    }
    repository.saveAllAndFlush(requirementTypes);
  }

  public List<RequirementType> listByTemplateId(Long id) {
    return repository.findByTemplateId(id).stream()
        .sorted()
        .toList();
  }

  public void deleteByTemplateId(Long templateId) {
    repository.deleteByTemplateId(templateId);
  }
}

package cn.linmt.quiet.service;

import cn.linmt.quiet.controller.requirement.dto.ListRequirement;
import cn.linmt.quiet.controller.requirement.dto.PlanningRequirement;
import cn.linmt.quiet.entity.QRequirement;
import cn.linmt.quiet.entity.Requirement;
import cn.linmt.quiet.enums.RequirementStatus;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.repository.RequirementRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequirementService {

  private final RequirementRepository repository;
  private final JPAQueryFactory queryFactory;

  @Transactional(rollbackFor = Exception.class)
  public Requirement save(Requirement requirement) {
    Requirement exist =
        repository.findByProjectIdAndTitleIgnoreCase(
            requirement.getProjectId(), requirement.getTitle());
    if (exist != null && !exist.getId().equals(requirement.getId())) {
      throw new BizException(112001);
    }
    if (requirement.getId() == null) {
      requirement.setStatus(RequirementStatus.TO_BE_PLANNED);
    }
    return repository.save(requirement);
  }

  public List<Requirement> list(ListRequirement list) {
    QRequirement requirement = QRequirement.requirement;
    BooleanBuilder predicate =
        Where.builder()
            .and(requirement.projectId.eq(list.getProjectId()))
            .notBlankContains(list.getTitle(), requirement.title)
            .isIdEq(list.getPriorityId(), requirement.priorityId)
            .isIdEq(list.getTypeId(), requirement.typeId)
            .notNullEq(list.getStatus(), requirement.status)
            .getPredicate();
    return queryFactory
        .selectFrom(requirement)
        .where(predicate)
        .orderBy(requirement.gmtUpdate.desc(), requirement.gmtCreate.desc())
        .offset(list.getOffset())
        .limit(list.getLimit())
        .fetch();
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  public List<Requirement> listByIterationId(Long iterationId) {
    return repository.findByIterationIdOrderByGmtUpdateDescGmtCreateDesc(iterationId);
  }

  public void planningRequirement(PlanningRequirement planningRequirement) {
    Long id = planningRequirement.getRequirementId();
    Requirement requirement = findById(id);
    if (!RequirementStatus.PLANNED.equals(requirement.getStatus())
        && !RequirementStatus.TO_BE_PLANNED.equals(requirement.getStatus())) {
      // TODO 可以修改需求的规划状态
      throw new BizException(112002);
    }
    Long iterationId = planningRequirement.getIterationId();
    if (iterationId == null) {
      requirement.setStatus(RequirementStatus.TO_BE_PLANNED);
    } else {
      requirement.setStatus(RequirementStatus.PLANNED);
    }
    repository.saveAndFlush(requirement);
    repository.updateIterationIdById(iterationId, id);
  }

  public Requirement findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new BizException(112000));
  }
}

package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.Iteration;
import cn.linmt.quiet.enums.PlanningStatus;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.repository.IterationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IterationService {

  private final IterationRepository repository;

  public Iteration save(Iteration iteration) {
    if (iteration.getId() == null) {
      iteration.setStatus(PlanningStatus.PLANNED);
    }
    Iteration exist =
        repository.findByVersionIdAndNameIgnoreCase(iteration.getVersionId(), iteration.getName());
    if (exist != null && !exist.getId().equals(iteration.getId())) {
      throw new BizException(109001);
    }
    return repository.save(iteration);
  }

  public Iteration getById(Long id) {
    return repository.findById(id).orElseThrow(() -> new BizException(109000));
  }

  public void deleteById(Long id) {
    Iteration iteration = getById(id);
    if (!PlanningStatus.PLANNED.equals(iteration.getStatus())) {
      throw new BizException(109002);
    }
  }

  public List<Iteration> listByVersionIds(List<Long> versionIds) {
    if (CollectionUtils.isEmpty(versionIds)) {
      return List.of();
    }
    return repository.findByVersionIdIn(versionIds);
  }

  public List<Iteration> listByVersionId(Long versionId) {
    return repository.findByVersionId(versionId);
  }
}

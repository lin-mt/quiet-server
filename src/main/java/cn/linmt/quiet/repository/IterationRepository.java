package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.Iteration;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;

public interface IterationRepository extends QuietRepository<Iteration> {
  Iteration findByVersionIdAndNameIgnoreCase(Long versionId, String name);

  List<Iteration> findByVersionIdIn(List<Long> versionIds);

  List<Iteration> findByVersionId(Long versionId);
}

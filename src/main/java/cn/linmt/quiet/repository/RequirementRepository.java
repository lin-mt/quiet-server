package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.Requirement;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

public interface RequirementRepository extends QuietRepository<Requirement> {
  Requirement findByProjectIdAndTitleIgnoreCase(Long projectId, String title);

  List<Requirement> findByIterationIdOrderByGmtUpdateDescGmtCreateDesc(Long iterationId);

  @Transactional
  @Modifying
  @Query("update Requirement r set r.iterationId = ?1 where r.id = ?2")
  void updateIterationIdById(@Nullable Long iterationId, Long id);
}

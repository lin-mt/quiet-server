package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.RequirementPriority;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;

public interface RequirementPriorityRepository extends QuietRepository<RequirementPriority> {
  List<RequirementPriority> findByTemplateId(Long templateId);

  void deleteByTemplateId(Long templateId);
}

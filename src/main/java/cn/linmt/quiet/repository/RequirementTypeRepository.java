package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.RequirementType;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;

public interface RequirementTypeRepository extends QuietRepository<RequirementType> {
  List<RequirementType> findByTemplateId(Long templateId);

  void deleteByTemplateId(Long templateId);
}

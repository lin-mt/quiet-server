package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.ApiDocsGroup;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.repository.ApiDocsGroupRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiDocsGroupService {

  private final ApiDocsGroupRepository repository;

  public ApiDocsGroup save(ApiDocsGroup group) {
    return repository.save(group);
  }

  public void deleteById(Long id) {
    repository
        .findById(id)
        .ifPresent(
            apiDocsGroup -> {
              Long childCount = repository.countByParentId(apiDocsGroup.getId());
              if (childCount > 0) {
                throw new BizException(114003);
              }
            });
    repository.deleteById(id);
  }

  public List<ApiDocsGroup> listByIds(Set<Long> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return List.of();
    }
    return repository.findAllById(ids);
  }
}

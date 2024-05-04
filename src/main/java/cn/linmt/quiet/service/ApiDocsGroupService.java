package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.ApiDocsGroup;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.repository.ApiDocsGroupRepository;
import java.util.List;
import java.util.Optional;
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
    Optional<ApiDocsGroup> docsGroup = repository.findById(id);
    docsGroup.ifPresent(
        apiDocsGroup -> {
          Long childCount = repository.countByParentId(apiDocsGroup.getId());
          if (childCount > 1) {
            Result.ADG_CANT_DELETE_CHILD.thr();
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

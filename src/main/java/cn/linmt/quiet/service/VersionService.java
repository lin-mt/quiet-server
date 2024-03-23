package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.Version;
import cn.linmt.quiet.enums.PlanningStatus;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.repository.VersionRepository;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionService {

  private final VersionRepository repository;

  public Version save(Version version) {
    if (version.getId() == null) {
      version.setStatus(PlanningStatus.PLANNED);
    }
    Version exist =
        repository.findByProjectIdAndNameIgnoreCase(version.getProjectId(), version.getName());
    if (exist != null && !exist.getId().equals(version.getId())) {
      Result.VERSION_NAME_EXIST.thr();
    }
    if (version.getParentId() != null) {
      List<Version> versions = listByProjectId(version.getProjectId());
      List<Version> children = getChildren(new ArrayList<>(versions), version.getId());
      if (children.stream()
              .map(Version::getId)
              .collect(Collectors.toSet())
              .contains(version.getParentId())
          || version.getParentId().equals(version.getId())) {
        Result.VERSION_PARENT_ERROR.thr();
      }
    }
    return repository.saveAndFlush(version);
  }

  private List<Version> getChildren(List<Version> versions, Long id) {
    if (CollectionUtils.isEmpty(versions)) {
      return new ArrayList<>();
    }
    List<Version> children = new ArrayList<>();
    Iterator<Version> iterator = versions.iterator();
    Set<Long> childrenIds = new HashSet<>();
    while (iterator.hasNext()) {
      Version next = iterator.next();
      if (id.equals(next.getParentId())) {
        children.add(next);
        childrenIds.add(next.getId());
        iterator.remove();
      }
    }
    if (CollectionUtils.isNotEmpty(childrenIds)) {
      for (Long childrenId : childrenIds) {
        children.addAll(getChildren(versions, childrenId));
      }
    }
    return children;
  }

  public void delete(Long id) {
    Version version = getById(id);
    if (!PlanningStatus.PLANNED.equals(version.getStatus())) {
      Result.VERSION_CANT_DEL_STATE.thr();
    }
    repository.deleteById(id);
  }

  public Version getById(Long id) {
    return repository.findById(id).orElseThrow(Result.VERSION_NOT_EXIST::exc);
  }

  public List<Version> listByProjectId(Long projectId) {
    return repository.findByProjectId(projectId).stream().sorted().toList();
  }

  public List<Version> listChildren(Long id) {
    return repository.findByParentId(id);
  }
}

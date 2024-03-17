package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.Version;
import cn.linmt.quiet.enums.PlanningStatus;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.repository.VersionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    return repository.saveAndFlush(version);
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

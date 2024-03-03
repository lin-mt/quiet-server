package cn.linmt.quiet.service;

import cn.linmt.quiet.controller.project.dto.PageProjectFilter;
import cn.linmt.quiet.entity.Project;
import cn.linmt.quiet.entity.QProject;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.repository.ProjectRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository repository;

  public Long save(Project save) {
    Project exist = repository.findByNameIgnoreCase(save.getName());
    if (exist != null && !exist.getId().equals(save.getId())) {
      Result.PRO_NAME_EXIST.thr();
    }
    return repository.saveAndFlush(save).getId();
  }

  public Project getById(Long id) {
    return repository.findById(id).orElseThrow(Result.PRO_NOT_EXIST::exc);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  public Page<Project> page(PageProjectFilter filter) {

    QProject project = QProject.project;
    BooleanBuilder predicate =
        Where.builder()
            .isIdEq(filter.getId(), project.id)
            .notBlankContains(filter.getName(), project.name)
            .notNullEq(filter.getBuildTool(), project.buildTool)
            .notBlankContains(filter.getGitAddress(), project.gitAddress)
            .notBlankContains(filter.getDescription(), project.description)
            .getPredicate();
    return repository.findAll(predicate, filter.pageable());
  }
}

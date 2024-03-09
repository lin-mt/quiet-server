package cn.linmt.quiet.service;

import cn.linmt.quiet.controller.project.dto.PageProjectFilter;
import cn.linmt.quiet.entity.Project;
import cn.linmt.quiet.entity.QProject;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.repository.ProjectRepository;
import com.querydsl.core.BooleanBuilder;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository repository;

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

  public List<Project> listByGroupId(Long id) {
    return repository.findByProjectGroupId(id);
  }

  public List<Project> listByTemplateId(Long templateId) {
    return StreamSupport.stream(
            repository.findAll(QProject.project.templateId.eq(templateId)).spliterator(), false)
        .toList();
  }

  public Long save(Project project) {
    Project exist = repository.findByNameIgnoreCase(project.getName());
    if (exist != null && !exist.getId().equals(project.getId())) {
      Result.PRO_NAME_EXIST.thr();
    }
    return repository.saveAndFlush(project).getId();
  }
}

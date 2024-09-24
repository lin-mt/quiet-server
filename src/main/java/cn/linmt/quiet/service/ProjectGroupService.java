package cn.linmt.quiet.service;

import cn.linmt.quiet.controller.projectgroup.dto.PageProjectGroup;
import cn.linmt.quiet.entity.ProjectGroup;
import cn.linmt.quiet.entity.QProjectGroup;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.repository.ProjectGroupRepository;
import com.querydsl.core.BooleanBuilder;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectGroupService {

  private final ProjectGroupRepository repository;

  public Long save(ProjectGroup projectGroup) {
    ProjectGroup exist = repository.findByNameIgnoreCase(projectGroup.getName());
    if (exist != null && !exist.getId().equals(projectGroup.getId())) {
      throw new BizException(107002);
    }
    return repository.saveAndFlush(projectGroup).getId();
  }

  public ProjectGroup getById(Long id) {
    return repository.findById(id).orElseThrow(() -> new BizException(107001));
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  public Page<ProjectGroup> page(PageProjectGroup page) {
    QProjectGroup projectGroup = QProjectGroup.projectGroup;
    BooleanBuilder predicate =
        Where.builder()
            .isIdEq(page.getId(), projectGroup.id)
            .notBlankContains(page.getName(), projectGroup.name)
            .notBlankContains(page.getDescription(), projectGroup.description)
            .getPredicate();
    return repository.findAll(predicate, page.pageable());
  }

  public List<ProjectGroup> listByIds(Set<Long> ids) {
    return repository.findAllById(ids);
  }
}

package cn.linmt.quiet.service;

import cn.linmt.quiet.controller.template.vo.PageTemplate;
import cn.linmt.quiet.entity.QTemplate;
import cn.linmt.quiet.entity.Template;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.repository.TemplateRepository;
import com.querydsl.core.BooleanBuilder;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TemplateService {

  private final TemplateRepository repository;

  @Transactional(rollbackFor = Exception.class)
  public Long save(Template template) {
    Template exist = repository.findByNameIgnoreCase(template.getName());
    if (exist != null && !exist.getId().equals(template.getId())) {
      throw new BizException(104001);
    }
    return repository.save(template).getId();
  }

  public Template getById(Long id) {
    return repository.findById(id).orElseThrow(() -> new BizException(104000));
  }

  public List<Template> listTemplate(String name) {
    BooleanBuilder predicate =
        Where.builder().notBlankContains(name, QTemplate.template.name).getPredicate();
    return StreamSupport.stream(repository.findAll(predicate).spliterator(), false).toList();
  }

  public Page<Template> page(PageTemplate pageTemplate) {
    QTemplate template = QTemplate.template;
    BooleanBuilder predicate =
        Where.builder()
            .isIdEq(pageTemplate.getId(), template.id)
            .notBlankContains(pageTemplate.getName(), template.name)
            .notBlankContains(pageTemplate.getDescription(), template.description)
            .getPredicate();
    return repository.findAll(predicate, pageTemplate.pageable());
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }
}

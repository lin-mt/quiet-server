package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.Template;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
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
      Result.TEMPLATE_NAME_EXIST.thr();
    }
    return repository.save(template).getId();
  }

  public Template getById(Long id) {
    return repository.findById(id).orElseThrow(Result.TEMPLATE_NOT_EXIST::exc);
  }
}

package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.Template;
import cn.linmt.quiet.framework.QuietRepository;

public interface TemplateRepository extends QuietRepository<Template> {
  Template findByNameIgnoreCase(String name);
}

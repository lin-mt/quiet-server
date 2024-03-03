package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.Role;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends QuietRepository<Role> {
  List<Role> findByCodeIn(Collection<String> code);

  Optional<Role> findByCode(String code);

  List<Role> findByParentId(Long parentId);

  Optional<Role> findByValueIgnoreCase(String value);
}

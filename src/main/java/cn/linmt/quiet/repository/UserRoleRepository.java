package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.UserRole;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends QuietRepository<UserRole> {
  List<UserRole> findByUserId(@NonNull Long userId);

  void deleteByRoleId(Long roleId);
}

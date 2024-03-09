package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.User;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.Optional;

public interface UserRepository extends QuietRepository<User> {
  Optional<User> findByUsername(String username);

  Optional<User> findByUsernameIgnoreCase(String username);
}

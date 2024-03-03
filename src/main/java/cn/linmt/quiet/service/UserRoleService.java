package cn.linmt.quiet.service;

import cn.linmt.quiet.action.DataInit;
import cn.linmt.quiet.entity.UserRole;
import cn.linmt.quiet.repository.UserRoleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {
  private final UserRoleRepository repository;

  public List<UserRole> listByUserId(Long userId) {
    return repository.findByUserId(userId);
  }

  public void save(UserRole userRole) {
    repository.save(userRole);
  }

  public void deleteByRoleId(Long roleId) {
    repository.deleteByRoleId(roleId);
  }

  public Set<Long> listRoleIdsByUserId(Long userId) {
    return repository.findByUserId(userId).stream()
        .map(UserRole::getRoleId)
        .filter(roleId -> !DataInit.RoleId.getAdmin().equals(roleId))
        .collect(Collectors.toSet());
  }

  public void updateRoles(Long userId, Set<Long> roleIds) {
    List<UserRole> remove =
        repository.findByUserId(userId).stream()
            .filter(
                ur ->
                    !DataInit.RoleId.getAdmin().equals(ur.getRoleId())
                        && (CollectionUtils.isEmpty(roleIds) || !roleIds.remove(ur.getRoleId())))
            .toList();
    if (CollectionUtils.isNotEmpty(remove)) {
      repository.deleteAll(remove);
    }
    if (CollectionUtils.isEmpty(roleIds)) {
      return;
    }
    List<UserRole> userRoles = new ArrayList<>();
    for (Long roleId : roleIds) {
      UserRole userRole = new UserRole();
      userRole.setUserId(userId);
      userRole.setRoleId(roleId);
      userRoles.add(userRole);
    }
    repository.saveAllAndFlush(userRoles);
  }
}

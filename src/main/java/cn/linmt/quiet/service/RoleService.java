package cn.linmt.quiet.service;

import cn.linmt.quiet.action.DataInit;
import cn.linmt.quiet.controller.role.dto.PageRole;
import cn.linmt.quiet.entity.QRole;
import cn.linmt.quiet.entity.Role;
import cn.linmt.quiet.enums.RoleCode;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.repository.RoleRepository;
import com.querydsl.core.BooleanBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

  private final RoleRepository repository;

  public List<Role> listById(Iterable<Long> ids) {
    return repository.findAllById(ids);
  }

  public List<Role> findByCodes(Collection<String> roleCode) {
    return repository.findByCodeIn(roleCode);
  }

  public List<Role> saveAll(Iterable<Role> roles) {
    if (IterableUtils.isEmpty(roles)) {
      return new ArrayList<>();
    }
    return repository.saveAll(roles);
  }

  public Page<Role> page(PageRole pageRole) {
    QRole role = QRole.role;
    BooleanBuilder predicate =
        Where.builder()
            .isIdEq(pageRole.getId(), role.id)
            .and(role.id.ne(DataInit.RoleId.getAdmin()))
            .isIdEq(pageRole.getParentId(), role.parentId)
            .notBlankContains(pageRole.getValue(), role.value)
            .notBlankContains(pageRole.getName(), role.name)
            .notBlankContains(pageRole.getCode(), role.code)
            .getPredicate();
    return repository.findAll(predicate, pageRole.pageable());
  }

  public List<Role> findAll() {
    return repository.findAll();
  }

  public Long save(Role role) {
    if (role.getId() != null) {
      repository.findById(role.getId()).orElseThrow(() -> new BizException(101000));
    }
    repository.findByValueIgnoreCase(role.getValue()).orElseThrow(() -> new BizException(101007));
    if (role.getParentId() == null) {
      role.setParentId(DataInit.RoleId.getAdmin());
    }
    if (role.getParentId().equals(role.getId())) {
      throw new BizException(101006);
    }
    Role parent =
        repository.findById(role.getParentId()).orElseThrow(() -> new BizException(101002));
    role.setCode(parent.getCode() + role.getCode());
    repository
        .findByCode(role.getCode())
        .ifPresent(
            exist -> {
              if (!exist.getId().equals(role.getId())) {
                throw new BizException(101001);
              }
            });
    for (RoleCode roleCode : RoleCode.values()) {
      if (roleCode.getCode().equals(role.getCode())) {
        throw new BizException(101004);
      }
    }
    return repository.save(role).getId();
  }

  public void deleteById(Long id) {
    Role role = getById(id);
    for (RoleCode roleCode : RoleCode.values()) {
      if (roleCode.getCode().equals(role.getCode())) {
        throw new BizException(101005);
      }
    }
    List<Role> children = repository.findByParentId(id);
    if (CollectionUtils.isNotEmpty(children)) {
      throw new BizException(101003);
    }
    repository.deleteById(id);
  }

  public Role getById(Long id) {
    return repository.findById(id).orElseThrow(() -> new BizException(101000));
  }

  public List<Role> listByIdContainsChild(List<Long> roleIds) {
    if (CollectionUtils.isEmpty(roleIds)) {
      return List.of();
    }
    List<Role> roles = listById(roleIds);
    if (CollectionUtils.isEmpty(roles)) {
      return List.of();
    }
    Set<String> codes = roles.stream().map(Role::getCode).collect(Collectors.toSet());
    Where where = Where.builder();
    for (String code : codes) {
      where.or(QRole.role.code.startsWith(code));
    }
    return StreamSupport.stream(repository.findAll(where.getPredicate()).spliterator(), false)
        .toList();
  }
}

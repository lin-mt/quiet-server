package cn.linmt.quiet.service;

import cn.linmt.quiet.controller.permission.dto.PagePermission;
import cn.linmt.quiet.entity.Permission;
import cn.linmt.quiet.entity.QPermission;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.repository.PermissionRepository;
import com.querydsl.core.BooleanBuilder;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {

  private final PermissionRepository repository;

  public List<Permission> findAll() {
    return repository.findAll().stream()
        .sorted(Comparator.comparingInt(Permission::getOrdinal))
        .toList();
  }

  public Permission save(Permission permission) {
    repository
        .findByTypeAndValueIgnoreCase(permission.getType(), permission.getValue())
        .ifPresent(
            exist -> {
              if (!exist.getId().equals(permission.getId())) {
                throw new BizException(102000);
              }
            });
    Long parentId = permission.getParentId();
    if (parentId != null) {
      if (parentId.equals(permission.getId())) {
        throw new BizException(102004);
      }
      repository.findById(parentId).orElseThrow(() -> new BizException(102001));
    }
    return repository.save(permission);
  }

  public Permission getById(Long id) {
    return repository.findById(id).orElseThrow(() -> new BizException(102002));
  }

  public void delete(Long id) {
    Permission permission = getById(id);
    List<Permission> children = repository.findByParentId(id);
    if (CollectionUtils.isNotEmpty(children)) {
      throw new BizException(102003);
    }
    repository.delete(permission);
  }

  public Page<Permission> page(PagePermission params) {
    QPermission permission = QPermission.permission;
    BooleanBuilder predicate =
        Where.builder()
            .isIdEq(params.getId(), permission.id)
            .isIdEq(params.getParentId(), permission.parentId)
            .notBlankContains(params.getName(), permission.name)
            .notBlankContains(params.getValue(), permission.value)
            .notNullEq(params.getType(), permission.type)
            .notBlankContains(params.getHttpUrl(), permission.httpUrl)
            .notNullEq(params.getHttpMethod(), permission.httpMethod)
            .notBlankContains(params.getDescription(), permission.description)
            .getPredicate();
    return repository.findAll(predicate, params.pageable());
  }

  /**
   * 根据ID查询权限，包含子权限
   *
   * @param ids ID集合
   * @return 所有权限
   */
  public List<Permission> listIncludeSubByIds(Collection<Long> ids) {
    if (IterableUtils.isEmpty(ids)) {
      return List.of();
    }
    List<Permission> all = findAll();
    Iterator<Permission> iterator = all.iterator();
    List<Permission> permissions = new ArrayList<>();
    while (iterator.hasNext()) {
      Permission permission = iterator.next();
      if (ids.contains(permission.getId())) {
        permissions.add(permission);
        iterator.remove();
      }
    }
    permissions.addAll(getSubPermissions(all, ids));
    return permissions;
  }

  private List<Permission> getSubPermissions(List<Permission> all, Collection<Long> ids) {
    if (CollectionUtils.isEmpty(all) || IterableUtils.isEmpty(ids)) {
      return List.of();
    }
    List<Permission> subPermissions = new ArrayList<>();
    Iterator<Permission> iterator = all.iterator();
    Set<Long> parentIds = new HashSet<>();
    while (iterator.hasNext()) {
      Permission permission = iterator.next();
      Long parentId = permission.getParentId();
      if (ids.contains(parentId)) {
        subPermissions.add(permission);
        parentIds.add(permission.getId());
        iterator.remove();
      }
    }
    subPermissions.addAll(getSubPermissions(all, parentIds));
    return subPermissions;
  }
}

package cn.linmt.quiet.manager;

import cn.linmt.quiet.controller.apidocs.vo.ApiDocsVO;
import cn.linmt.quiet.controller.apidocsgroup.vo.ApiDocsGroupDetail;
import cn.linmt.quiet.controller.apidocsgroup.vo.ApiDocsGroupVO;
import cn.linmt.quiet.entity.ApiDocs;
import cn.linmt.quiet.entity.ApiDocsGroup;
import cn.linmt.quiet.entity.QApiDocs;
import cn.linmt.quiet.entity.QApiDocsGroup;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.service.ApiDocsGroupService;
import cn.linmt.quiet.service.ApiDocsService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiDocsGroupManager {

  private final ApiDocsGroupService apiDocsGroupService;
  private final ApiDocsService apiDocsService;
  private final JPAQueryFactory queryFactory;

  public void delete(Long id) {
    List<ApiDocs> apiDocs = apiDocsService.listByGroupId(id);
    if (CollectionUtils.isNotEmpty(apiDocs)) {
      Result.ADG_CANT_DELETE.thr();
    }
    apiDocsGroupService.deleteById(id);
  }

  @Transactional
  public List<ApiDocsGroupDetail> treeApiDocsGroupDetail(Long projectId, String name) {
    QApiDocsGroup apiDocsGroup = QApiDocsGroup.apiDocsGroup;
    QApiDocs apiDocs = QApiDocs.apiDocs;
    BooleanBuilder predicate =
        Where.builder()
            .isIdEq(projectId, apiDocsGroup.projectId)
            .with(
                where -> {
                  if (StringUtils.isNotBlank(name)) {
                    where.and(
                        apiDocsGroup
                            .name
                            .contains(name)
                            .or(apiDocs.name.contains(name))
                            .or(apiDocs.path.contains(name)));
                  }
                })
            .getPredicate();
    List<ApiDocsGroup> docsGroups =
        queryFactory
            .selectFrom(apiDocsGroup)
            .leftJoin(apiDocs)
            .on(apiDocsGroup.id.eq(apiDocs.groupId))
            .where(predicate)
            .stream()
            .toList();
    List<ApiDocsGroupDetail> result = new ArrayList<>();
    Map<Long, ApiDocsGroupDetail> groupMap = new HashMap<>();
    Set<Long> groupIds = new HashSet<>();
    Set<Long> parentIds = new HashSet<>();
    for (ApiDocsGroup group : docsGroups) {
      ApiDocsGroupDetail detail = new ApiDocsGroupDetail();
      BeanUtils.copyProperties(group, detail);
      groupIds.add(group.getId());
      if (group.getParentId() == null) {
        result.add(detail);
      } else {
        parentIds.add(group.getParentId());
      }
      groupMap.put(group.getId(), detail);
    }
    result.stream().map(ApiDocsGroupDetail::getId).forEach(parentIds::remove);
    while (CollectionUtils.isNotEmpty(parentIds)) {
      List<ApiDocsGroup> groups = apiDocsGroupService.listByIds(parentIds);
      parentIds.clear();
      for (ApiDocsGroup group : groups) {
        ApiDocsGroupDetail detail = new ApiDocsGroupDetail();
        BeanUtils.copyProperties(group, detail);
        if (group.getParentId() == null) {
          result.add(detail);
        } else {
          parentIds.add(group.getParentId());
        }
        groupMap.put(group.getId(), detail);
      }
    }
    Map<Long, List<ApiDocsVO>> docsMap = new HashMap<>();
    if (CollectionUtils.isNotEmpty(groupIds)) {
      BooleanBuilder apiDocsPredicate =
          Where.builder()
              .notEmptyIn(groupIds, apiDocs.groupId)
              .with(
                  where -> {
                    if (StringUtils.isNotBlank(name)) {
                      where.and(apiDocs.name.contains(name).or(apiDocs.path.contains(name)));
                    }
                  })
              .getPredicate();
      docsMap.putAll(
          queryFactory.selectFrom(apiDocs).where(apiDocsPredicate).stream()
              .map(
                  docs -> {
                    ApiDocsVO vo = new ApiDocsVO();
                    BeanUtils.copyProperties(docs, vo);
                    return vo;
                  })
              .collect(Collectors.groupingBy(ApiDocsVO::getGroupId)));
    }
    groupMap.forEach(
        (k, v) -> {
          Long parentId = v.getParentId();
          v.setApiDocs(docsMap.get(v.getId()));
          if (parentId == null) {
            return;
          }
          ApiDocsGroupVO groupVO = groupMap.get(parentId);
          if (groupVO.getChildren() == null) {
            groupVO.setChildren(new ArrayList<>());
          }
          groupVO.getChildren().add(v);
        });
    return result;
  }
}

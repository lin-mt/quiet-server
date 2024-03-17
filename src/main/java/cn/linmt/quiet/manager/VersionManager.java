package cn.linmt.quiet.manager;

import cn.linmt.quiet.controller.iteration.vo.IterationVO;
import cn.linmt.quiet.controller.iteration.vo.SimpleIteration;
import cn.linmt.quiet.controller.iteration.vo.VersionDetail;
import cn.linmt.quiet.controller.version.vo.TreeVersionDetail;
import cn.linmt.quiet.controller.version.vo.VersionVO;
import cn.linmt.quiet.entity.Iteration;
import cn.linmt.quiet.entity.Version;
import cn.linmt.quiet.service.IterationService;
import cn.linmt.quiet.service.VersionService;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionManager {

  private final VersionService versionService;
  private final IterationService iterationService;

  public void delete(Long id) {
    // TODO 处理该版本下的迭代信息，将删除的版本下的其他迭代信息移动到下一个版本
    versionService.delete(id);
  }

  public List<TreeVersionDetail> treeVersion(Long projectId) {
    List<Version> versions = versionService.listByProjectId(projectId);
    Map<Long, TreeVersionDetail> id2detail = new HashMap<>();
    Map<Long, List<TreeVersionDetail>> id2children = new HashMap<>();
    List<TreeVersionDetail> details = new ArrayList<>();
    List<Long> versionIds = new ArrayList<>();
    for (Version version : versions) {
      versionIds.add(version.getId());
      TreeVersionDetail detail = new TreeVersionDetail();
      BeanUtils.copyProperties(version, detail);
      id2detail.put(detail.getId(), detail);
      if (version.getParentId() == null) {
        details.add(detail);
      } else {
        id2children.computeIfAbsent(version.getParentId(), key -> new ArrayList<>()).add(detail);
      }
    }
    id2children.forEach((parentId, children) -> id2detail.get(parentId).setChildren(children));
    Map<Long, List<Iteration>> versionId2iterations =
        iterationService.listByVersionIds(versionIds).stream()
            .collect(Collectors.groupingBy(Iteration::getVersionId));
    versionId2iterations.forEach(
        (versionId, iterations) -> {
          TreeVersionDetail detail = id2detail.get(versionId);
          detail.setIterations(
              iterations.stream()
                  .map(
                      iteration -> {
                        SimpleIteration simpleIteration = new SimpleIteration();
                        BeanUtils.copyProperties(iteration, simpleIteration);
                        return simpleIteration;
                      })
                  .toList());
        });
    return details;
  }

  public VersionDetail getVersionDetail(Long versionId) {
    Version version = versionService.getById(versionId);
    VersionDetail versionDetail = new VersionDetail();
    BeanUtils.copyProperties(version, versionDetail);
    List<IterationVO> iterations =
        iterationService.listByVersionId(versionId).stream()
            .map(
                iteration -> {
                  IterationVO vo = new IterationVO();
                  BeanUtils.copyProperties(iteration, vo);
                  return vo;
                })
            .toList();
    List<VersionVO> children =
        versionService.listChildren(versionId).stream()
            .map(
                v -> {
                  VersionVO vo = new VersionVO();
                  BeanUtils.copyProperties(v, vo);
                  return vo;
                })
            .toList();
    versionDetail.setChildren(children);
    versionDetail.setIterations(iterations);
    return versionDetail;
  }
}

package cn.linmt.quiet.controller.version;

import cn.linmt.quiet.controller.iteration.vo.VersionDetail;
import cn.linmt.quiet.controller.version.dto.AddVersion;
import cn.linmt.quiet.controller.version.dto.UpdateVersion;
import cn.linmt.quiet.controller.version.vo.TreeVersionDetail;
import cn.linmt.quiet.entity.Version;
import cn.linmt.quiet.manager.VersionManager;
import cn.linmt.quiet.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/version")
public class VersionController {

  private final VersionService versionService;
  private final VersionManager versionManager;

  @PostMapping
  @Operation(summary = "添加项目版本信息")
  public Long addVersion(@RequestBody AddVersion add) {
    Version version = new Version();
    BeanUtils.copyProperties(add, version);
    return versionService.save(version).getId();
  }

  @PutMapping
  @Operation(summary = "更新项目版本信息")
  public Long updateVersion(@RequestBody UpdateVersion update) {
    Version version = new Version();
    BeanUtils.copyProperties(update, version);
    return versionService.save(version).getId();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除项目版本信息")
  public void deleteVersion(@PathVariable Long id) {
    versionManager.delete(id);
  }

  @GetMapping("/{id}")
  @Operation(summary = "获取版本详细信息")
  public VersionDetail getVersionDetail(@PathVariable Long id) {
    return versionManager.getVersionDetail(id);
  }

  @GetMapping("/treeVersionDetail")
  @Operation(summary = "获取项目所有版本信息（包含迭代信息）并返回树形结构")
  public List<TreeVersionDetail> treeVersionDetail(@RequestParam Long projectId) {
    return versionManager.treeVersion(projectId);
  }
}

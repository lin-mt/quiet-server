package cn.linmt.quiet.controller.apidocsgroup;

import cn.linmt.quiet.controller.apidocsgroup.dto.SaveApiDocsGroup;
import cn.linmt.quiet.controller.apidocsgroup.dto.UpdateApiDocsGroup;
import cn.linmt.quiet.controller.apidocsgroup.vo.ApiDocsGroupDetail;
import cn.linmt.quiet.controller.apidocsgroup.vo.ApiDocsGroupVO;
import cn.linmt.quiet.entity.ApiDocsGroup;
import cn.linmt.quiet.manager.ApiDocsGroupManager;
import cn.linmt.quiet.service.ApiDocsGroupService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apiDocsGroup")
public class ApiDocsGroupController {

  private final ApiDocsGroupService apiDocsGroupService;
  private final ApiDocsGroupManager apiDocsGroupManager;

  @PostMapping
  @Operation(summary = "新增接口文档分组")
  public Long addApiDocsGroup(@RequestBody SaveApiDocsGroup saveApiDocsGroup) {
    ApiDocsGroup apiDocsGroup = new ApiDocsGroup();
    BeanUtils.copyProperties(saveApiDocsGroup, apiDocsGroup);
    return apiDocsGroupService.save(apiDocsGroup).getId();
  }

  @PutMapping
  @Operation(summary = "更新接口文档分组")
  public ApiDocsGroupVO updateApiDocsGroup(@RequestBody UpdateApiDocsGroup updateApiDocsGroup) {
    ApiDocsGroup apiDocsGroup = new ApiDocsGroup();
    BeanUtils.copyProperties(updateApiDocsGroup, apiDocsGroup);
    ApiDocsGroup group = apiDocsGroupService.save(apiDocsGroup);
    ApiDocsGroupVO groupVO = new ApiDocsGroupVO();
    BeanUtils.copyProperties(group, groupVO);
    return groupVO;
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除接口文档分组")
  public void deleteApiDocsGroup(@PathVariable Long id) {
    apiDocsGroupManager.delete(id);
  }

  @GetMapping("/treeApiDocsGroupDetail")
  @Operation(summary = "查询项目接口文档信息")
  public List<ApiDocsGroupDetail> treeApiDocsGroupDetail(
      @RequestParam Long projectId, @RequestParam(required = false) String name) {
    return apiDocsGroupManager.treeApiDocsGroupDetail(projectId, name);
  }
}

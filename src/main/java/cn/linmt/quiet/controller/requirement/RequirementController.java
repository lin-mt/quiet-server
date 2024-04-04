package cn.linmt.quiet.controller.requirement;

import cn.linmt.quiet.controller.requirement.dto.*;
import cn.linmt.quiet.controller.requirement.vo.RequirementTask;
import cn.linmt.quiet.controller.requirement.vo.RequirementVO;
import cn.linmt.quiet.entity.Requirement;
import cn.linmt.quiet.manager.RequirementManager;
import cn.linmt.quiet.service.RequirementService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requirement")
public class RequirementController {

  private final RequirementService requirementService;
  private final RequirementManager requirementManager;

  @PostMapping
  @Operation(summary = "添加需求")
  public Long addRequirement(@RequestBody AddRequirement addRequirement) {
    Requirement requirement = new Requirement();
    BeanUtils.copyProperties(addRequirement, requirement);
    return requirementService.save(requirement).getId();
  }

  @PutMapping
  @Operation(summary = "更新需求")
  public Long updateRequirement(@RequestBody UpdateRequirement updateRequirement) {
    Requirement requirement = new Requirement();
    BeanUtils.copyProperties(updateRequirement, requirement);
    return requirementService.save(requirement).getId();
  }

  @PutMapping("/planning")
  @Operation(summary = "规划需求")
  public void planningRequirement(@RequestBody PlanningRequirement planningRequirement) {
    requirementService.planningRequirement(planningRequirement);
  }

  @GetMapping("/list")
  @Operation(summary = "查询需求")
  public List<RequirementVO> listRequirement(ListRequirement listRequirement) {
    return requirementService.list(listRequirement).stream()
        .map(
            requirement -> {
              RequirementVO vo = new RequirementVO();
              BeanUtils.copyProperties(requirement, vo);
              return vo;
            })
        .toList();
  }

  @GetMapping("/listByIterationId")
  @Operation(summary = "查询需求")
  public List<RequirementVO> listRequirementByIterationId(@RequestParam Long iterationId) {
    return requirementService.listByIterationId(iterationId).stream()
        .map(
            requirement -> {
              RequirementVO vo = new RequirementVO();
              BeanUtils.copyProperties(requirement, vo);
              return vo;
            })
        .toList();
  }

  @GetMapping("/requirementTask")
  @Operation(summary = "查询需求任务详情")
  public List<RequirementTask> requirementTask(ListRequirementTask listRequirementTask) {
    return requirementManager.requirementTask(listRequirementTask);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除需求")
  public void deleteRequirement(@PathVariable Long id) {
    requirementManager.deleteById(id);
  }
}

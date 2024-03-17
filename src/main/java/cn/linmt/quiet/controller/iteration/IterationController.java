package cn.linmt.quiet.controller.iteration;

import cn.linmt.quiet.controller.iteration.dto.AddIteration;
import cn.linmt.quiet.controller.iteration.dto.UpdateIteration;
import cn.linmt.quiet.controller.iteration.vo.IterationDetail;
import cn.linmt.quiet.entity.Iteration;
import cn.linmt.quiet.manager.IterationManager;
import cn.linmt.quiet.service.IterationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/iteration")
public class IterationController {

  private final IterationManager iterationManager;
  private final IterationService iterationService;

  @PostMapping
  @Operation(summary = "添加项目迭代信息")
  public Long addIteration(@RequestBody AddIteration add) {
    Iteration iteration = new Iteration();
    BeanUtils.copyProperties(add, iteration);
    return iterationService.save(iteration).getId();
  }

  @PutMapping
  @Operation(summary = "更新项目迭代信息")
  public Long updateIteration(@RequestBody UpdateIteration update) {
    Iteration iteration = new Iteration();
    BeanUtils.copyProperties(update, iteration);
    return iterationService.save(iteration).getId();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除项目迭代信息")
  public void deleteIteration(@PathVariable Long id) {
    iterationManager.deleteById(id);
  }

  @GetMapping("/{id}")
  @Operation(summary = "获取迭代详细信息")
  public IterationDetail getIterationDetail(@PathVariable Long id) {
    return iterationManager.getIterationDetail(id);
  }
}

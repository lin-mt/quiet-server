package cn.linmt.quiet.manager;

import cn.linmt.quiet.controller.iteration.vo.IterationDetail;
import cn.linmt.quiet.entity.Iteration;
import cn.linmt.quiet.service.IterationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IterationManager {

  private final IterationService iterationService;

  public void deleteById(Long id) {
    // TODO 将该迭代下的需求移动到下一个迭代
    iterationService.deleteById(id);
  }

  public IterationDetail getIterationDetail(Long id) {
    Iteration iteration = iterationService.getById(id);
    IterationDetail detail = new IterationDetail();
    BeanUtils.copyProperties(iteration, detail);
    return detail;
  }
}

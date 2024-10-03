package cn.linmt.quiet.controller.repository;

import cn.linmt.quiet.controller.repository.dto.AddRepository;
import cn.linmt.quiet.controller.repository.dto.PageRepository;
import cn.linmt.quiet.controller.repository.dto.UpdateRepository;
import cn.linmt.quiet.controller.repository.vo.RepositoryVO;
import cn.linmt.quiet.entity.Repository;
import cn.linmt.quiet.manager.RepositoryManager;
import cn.linmt.quiet.service.RepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/repository")
public class RepositoryController {

  private final RepositoryService repositoryService;
  private final RepositoryManager repositoryManager;

  @PostMapping
  @Operation(summary = "新增仓库")
  public Long addRepository(@RequestBody @Validated AddRepository repository) throws Exception {
    Repository add = new Repository();
    BeanUtils.copyProperties(repository, add);
    return repositoryService.save(add);
  }

  @PutMapping
  @Operation(summary = "更新仓库")
  public Long updateRepository(@RequestBody @Validated UpdateRepository repository)
      throws Exception {
    Repository add = new Repository();
    BeanUtils.copyProperties(repository, add);
    return repositoryService.save(add);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除仓库")
  public void deleteRepository(@PathVariable Long id) {
    repositoryManager.deleteRepository(id);
  }

  @GetMapping("/page")
  @Operation(summary = "分页查询仓库列表")
  public Page<RepositoryVO> pageRepository(PageRepository pageRepository) {
    Page<Repository> repositories = repositoryService.page(pageRepository);
    return repositories.map(
        r -> {
          RepositoryVO repositoryVO = new RepositoryVO();
          BeanUtils.copyProperties(r, repositoryVO);
          return repositoryVO;
        });
  }

  @GetMapping("/list")
  @Operation(summary = "查询仓库")
  public List<RepositoryVO> listRepository(@RequestParam(required = false) String name) {
    return repositoryService.listByName(name).stream()
        .map(
            repository -> {
              RepositoryVO repositoryVO = new RepositoryVO();
              BeanUtils.copyProperties(repository, repositoryVO);
              return repositoryVO;
            })
        .toList();
  }
}

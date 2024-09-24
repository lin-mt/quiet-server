package cn.linmt.quiet.controller.apidocs;

import cn.linmt.quiet.controller.apidocs.vo.SaveApiDocs;
import cn.linmt.quiet.controller.apidocs.vo.UpdateApiDocs;
import cn.linmt.quiet.entity.ApiDocs;
import cn.linmt.quiet.enums.ApiDocsState;
import cn.linmt.quiet.manager.ApiDocsManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apiDocs")
public class ApiDocsController {
  private final ApiDocsManager apiDocsManager;

  @PostMapping
  @Operation(summary = "新增接口文档")
  public Long addApiDocs(@RequestBody SaveApiDocs save) {
    ApiDocs apiDocs = new ApiDocs();
    BeanUtils.copyProperties(save, apiDocs);
    apiDocs.setState(ApiDocsState.DESIGN);
    return apiDocsManager.save(apiDocs).getId();
  }

  @PutMapping
  @Operation(summary = "更新接口文档")
  public Long updateApiDocs(@RequestBody UpdateApiDocs update) {
    ApiDocs apiDocs = new ApiDocs();
    BeanUtils.copyProperties(update, apiDocs);
    return apiDocsManager.save(apiDocs).getId();
  }
}

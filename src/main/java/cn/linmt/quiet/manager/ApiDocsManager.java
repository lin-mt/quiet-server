package cn.linmt.quiet.manager;

import cn.linmt.quiet.entity.ApiDocs;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.service.ApiDocsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiDocsManager {

  private final ApiDocsService apiDocsService;

  public ApiDocs save(ApiDocs apiDocs) {
    ApiDocs exist = apiDocsService.findByMethodAndUri(apiDocs.getMethod(), apiDocs.getUri());
    if (exist != null && !exist.getId().equals(apiDocs.getId())) {
      throw new BizException(115000);
    }
    return apiDocsService.save(apiDocs);
  }
}

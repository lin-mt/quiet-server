package cn.linmt.quiet.manager;

import cn.linmt.quiet.entity.ApiDocs;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.service.ApiDocsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiDocsManager {

  private final ApiDocsService apiDocsService;

  public ApiDocs save(ApiDocs apiDocs) {
    ApiDocs exist = apiDocsService.findByMethodAndPath(apiDocs.getMethod(), apiDocs.getPath());
    if (exist != null && !exist.getId().equals(apiDocs.getId())) {
      Result.API_DOCS_EXIST.thr();
    }
    return apiDocsService.save(apiDocs);
  }
}

package cn.linmt.quiet.repository;

import cn.linmt.quiet.entity.ApiDocs;
import cn.linmt.quiet.enums.HttpMethod;
import cn.linmt.quiet.framework.QuietRepository;
import java.util.List;

public interface ApiDocsRepository extends QuietRepository<ApiDocs> {
  List<ApiDocs> findByGroupId(Long groupId);

  ApiDocs findByMethodAndUri(HttpMethod method, String uri);
}

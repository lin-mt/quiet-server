package cn.linmt.quiet.service;

import cn.linmt.quiet.entity.ApiDocs;
import cn.linmt.quiet.enums.HttpMethod;
import cn.linmt.quiet.repository.ApiDocsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiDocsService {

  private final ApiDocsRepository apiDocsRepository;

  public List<ApiDocs> listByGroupId(Long groupId) {
    return apiDocsRepository.findByGroupId(groupId);
  }

  public ApiDocs findByMethodAndPath(HttpMethod method, String path) {
    return apiDocsRepository.findByMethodAndPath(method, path);
  }

  public ApiDocs save(ApiDocs apiDocs) {
    return apiDocsRepository.saveAndFlush(apiDocs);
  }
}

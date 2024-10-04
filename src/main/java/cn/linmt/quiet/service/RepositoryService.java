package cn.linmt.quiet.service;

import cn.linmt.quiet.config.properties.ServerProperties;
import cn.linmt.quiet.controller.repository.dto.PageRepository;
import cn.linmt.quiet.entity.QRepository;
import cn.linmt.quiet.entity.Repository;
import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.repository.RepositoryRepository;
import cn.linmt.quiet.util.EncryptionUtils;
import com.querydsl.core.BooleanBuilder;
import java.util.*;
import java.util.stream.StreamSupport;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepositoryService {

  private final RepositoryRepository repositoryRepository;
  private final ServerProperties serverProperties;

  public Long save(Repository repository) throws Exception {
    Repository exist = repositoryRepository.findByName(repository.getName());
    if (exist != null && !exist.getId().equals(repository.getId())) {
      throw new BizException(116000);
    }
    if (repository.getId() == null) {
      // 加密密钥（从安全位置获取，比如从环境变量或密钥管理服务）
      String secretKeyBase64 = serverProperties.getSecretKey();
      SecretKey secretKey = EncryptionUtils.decodeKeyFromString(secretKeyBase64);
      // 生成IV（初始化向量）
      byte[] iv = EncryptionUtils.generateIV();
      if (StringUtils.isNotEmpty(repository.getAccessToken())) {
        String encryptedToken = EncryptionUtils.encrypt(repository.getAccessToken(), secretKey, iv);
        repository.setAccessToken(encryptedToken);
      }
      if (StringUtils.isNotEmpty(repository.getPassword())
          && StringUtils.isNotEmpty(repository.getUsername())) {
        // 加密用户名、密码和Token
        String encryptedUsername = EncryptionUtils.encrypt(repository.getUsername(), secretKey, iv);
        String encryptedPassword = EncryptionUtils.encrypt(repository.getPassword(), secretKey, iv);
        repository.setUsername(encryptedUsername);
        repository.setPassword(encryptedPassword);
      }
      repository.setIv(Base64.getEncoder().encodeToString(iv));
    } else if (exist == null) {
      throw new BizException(116001);
    }
    return repositoryRepository.save(repository).getId();
  }

  public Page<Repository> page(PageRepository page) {
    QRepository repository = QRepository.repository;
    BooleanBuilder predicate =
        Where.builder()
            .isIdEq(page.getId(), repository.id)
            .notNullEq(page.getType(), repository.type)
            .notBlankContains(page.getName(), repository.name)
            .notNullEq(page.getBuildTool(), repository.buildTool)
            .getPredicate();
    return repositoryRepository.findAll(predicate, page.pageable());
  }

  public void deleteById(Long id) {
    repositoryRepository.deleteById(id);
  }

  public List<Repository> listByName(String name) {
    BooleanBuilder predicate =
        Where.builder().notBlankContains(name, QRepository.repository.name).getPredicate();
    Iterable<Repository> all = repositoryRepository.findAll(predicate);
    return StreamSupport.stream(all.spliterator(), false).toList();
  }

  public List<Repository> findAllByIds(Collection<Long> ids) {
    if (CollectionUtils.isEmpty(ids)) {
      return new ArrayList<>();
    }
    return repositoryRepository.findAllById(ids);
  }
}

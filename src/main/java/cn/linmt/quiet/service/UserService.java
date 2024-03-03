package cn.linmt.quiet.service;

import cn.linmt.quiet.controller.user.dto.PageUser;
import cn.linmt.quiet.controller.user.vo.SimpleUser;
import cn.linmt.quiet.entity.QUser;
import cn.linmt.quiet.entity.User;
import cn.linmt.quiet.enums.Enabled;
import cn.linmt.quiet.enums.Expired;
import cn.linmt.quiet.enums.Locked;
import cn.linmt.quiet.framework.Where;
import cn.linmt.quiet.modal.http.Result;
import cn.linmt.quiet.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JPAQueryFactory queryFactory;

  public User findByUsername(String username) {
    return repository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }

  public Optional<User> findByUsernameOption(String username) {
    return repository.findByUsername(username);
  }

  public Long registration(User user) {
    repository
        .findByUsernameIgnoreCase(user.getUsername())
        .ifPresent(exist -> Result.USERNAME_EXIST.thr());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setEnabled(Enabled.YES);
    user.setAccountLocked(Locked.NO);
    user.setAccountExpired(Expired.NO);
    user.setCredentialsExpired(Expired.NO);
    return repository.save(user).getId();
  }

  public Page<User> page(@NotNull PageUser filter) {
    QUser user = QUser.user;
    BooleanBuilder predicate =
        Where.builder()
            .isIdEq(filter.getId(), user.id)
            .notNullEq(filter.getEnabled(), user.enabled)
            .notBlankContains(filter.getUsername(), user.username)
            .notNullEq(filter.getAccountLocked(), user.accountLocked)
            .notNullEq(filter.getAccountExpired(), user.accountExpired)
            .notNullEq(filter.getCredentialsExpired(), user.credentialsExpired)
            .getPredicate();
    return repository.findAll(predicate, filter.pageable());
  }

  @Modifying
  public Long update(User user) {
    repository
        .findByUsernameIgnoreCase(user.getUsername())
        .ifPresent(
            exist -> {
              if (!exist.getId().equals(user.getId())) {
                Result.USERNAME_EXIST.thr();
              }
            });
    return repository.saveAndFlush(user).getId();
  }

  public void delete(Long id) {
    User user = this.getById(id);
    // 删除 user_role
    repository.delete(user);
  }

  private User getById(Long id) {
    return repository.findById(id).orElseThrow(Result.USER_NOT_EXIST::exc);
  }

  public List<User> listById(Iterable<Long> ids) {
    if (IterableUtils.isEmpty(ids)) {
      return List.of();
    }
    return repository.findAllById(ids);
  }

  @Transactional
  public List<SimpleUser> listUser(String username, long limit) {
    QUser user = QUser.user;
    BooleanBuilder predicate =
        Where.builder().notBlankContains(username, user.username).getPredicate();
    return queryFactory.selectFrom(user).where(predicate).limit(limit).stream()
        .map(
            u -> {
              SimpleUser simpleUser = new SimpleUser();
              simpleUser.setId(u.getId());
              simpleUser.setUsername(u.getUsername());
              return simpleUser;
            })
        .toList();
  }
}

package cn.linmt.quiet.config;

import cn.linmt.quiet.modal.app.UserInfo;
import java.util.Optional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class ServerAuditorAware implements AuditorAware<Long> {
  @NonNull
  @Override
  public Optional<Long> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();
      if (principal instanceof UserInfo userInfo) {
        return Optional.of(userInfo.getId());
      }
    }
    return Optional.empty();
  }
}

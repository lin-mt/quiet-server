package cn.linmt.quiet.util;

import cn.linmt.quiet.exception.BizException;
import cn.linmt.quiet.modal.app.UserInfo;
import cn.linmt.quiet.modal.http.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {

  public static UserInfo getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();
    if (authentication.isAuthenticated() && principal instanceof UserInfo userInfo) {
      return userInfo;
    }
    throw new BizException(Result.UNKNOWN_USER);
  }

  public static Long getUserId() {
    return getUser().getId();
  }
}

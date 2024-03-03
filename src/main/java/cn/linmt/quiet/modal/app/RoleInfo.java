package cn.linmt.quiet.modal.app;

import cn.linmt.quiet.entity.Role;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;

public class RoleInfo extends Role implements GrantedAuthority, Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Override
  public String getAuthority() {
    return getValue();
  }
}

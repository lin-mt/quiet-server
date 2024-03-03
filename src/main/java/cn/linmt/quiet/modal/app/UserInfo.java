package cn.linmt.quiet.modal.app;

import cn.linmt.quiet.entity.User;
import cn.linmt.quiet.enums.Enabled;
import cn.linmt.quiet.enums.Expired;
import cn.linmt.quiet.enums.Locked;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class UserInfo extends User implements UserDetails {

  private List<RoleInfo> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public boolean isAccountNonExpired() {
    return Expired.NO.equals(getAccountExpired());
  }

  @Override
  public boolean isAccountNonLocked() {
    return Locked.NO.equals(getAccountLocked());
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return Expired.NO.equals(getCredentialsExpired());
  }

  @Override
  public boolean isEnabled() {
    return Enabled.YES.equals(getEnabled());
  }
}

package cn.linmt.quiet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@AllArgsConstructor
public enum PermissionType {
  /** 菜单 */
  MENU("menu"),
  /** 按钮 */
  BUTTON("btn"),
  /** 接口 */
  API("api");

  private final String prefix;
}

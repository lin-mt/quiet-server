package cn.linmt.quiet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@AllArgsConstructor
public enum RoleCode {
  Admin("管理员", "Administrator", ""),
  OrdinaryUser("普通用户", "User", "00"),
  ProjectAdmin("产品管理", "ProductAdmin", "01"),
  ProductManager("产品经理", "ProductManager", "0101"),
  ProjectManager("项目经理", "ProjectManager", "010101");

  public static final int LEVEL_LENGTH = 2;

  private final String name;
  private final String value;
  private final String code;

  public boolean isRole(String roleCode) {
    return code.equals(roleCode);
  }

  public boolean hasPermission(String roleCode) {
    if (StringUtils.isBlank(roleCode)) {
      return false;
    }
    return code.length() >= roleCode.length() && roleCode.startsWith(code);
  }
}

package cn.linmt.quiet.modal.http;

import cn.linmt.quiet.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Result {
  SUCCESS(200, "成功", MessageType.SILENT),
  EXCEPTION(500, "服务器发生错误", MessageType.ERROR_MESSAGE),
  ACCESS_DENIED(403, "无权限访问", MessageType.ERROR_MESSAGE),
  UNKNOWN_USER(900, "未知用户，请重新登录", MessageType.WARN_MESSAGE),
  /** 100xxx 用户 */
  USER_NOT_EXIST(100000, "用户不存在", MessageType.ERROR_MESSAGE),
  USERNAME_EXIST(100001, "用户名已存在", MessageType.WARN_MESSAGE),
  /** 101xxx 角色 */
  ROLE_NOT_EXIST(101000, "角色不存在", MessageType.ERROR_MESSAGE),
  ROLE_CODE_EXIST(101001, "角色编码已存在", MessageType.ERROR_MESSAGE),
  ROLE_PARENT_NOT_EXIST(101002, "父角色不存在", MessageType.ERROR_MESSAGE),
  ROLE_CANT_DEL_CHILD(101003, "存在子角色，无法删除", MessageType.WARN_MESSAGE),
  ROLE_SYS_CODE(101004, "禁止更新、使用系统角色编码", MessageType.WARN_MESSAGE),
  ROLE_CANT_DEL_SYS(101005, "系统角色无法删除", MessageType.WARN_MESSAGE),
  ROLE_PARENT_CANT_SELF(101006, "角色的父角色不能为角色本身", MessageType.ERROR_MESSAGE),
  ROLE_VALUE_EXIST(101007, "该角色值已存在", MessageType.WARN_MESSAGE),
  /** 102xxx 权限 */
  PER_EXIST(102000, "已存在该权限信息", MessageType.WARN_MESSAGE),
  PER_PARENT_NOT_EXIST(102001, "父权限信息不存在", MessageType.WARN_MESSAGE),
  PER_NOT_EXIST(102002, "权限信息不存在", MessageType.ERROR_MESSAGE),
  PER_CANT_DEL_PAR(102003, "存在子权限信息，无法删除", MessageType.WARN_MESSAGE),
  PER_PARENT_CANT_SELF(102004, "权限的父权限不能为权限本身", MessageType.ERROR_MESSAGE),
  /** 103xxx 项目 */
  PRO_NOT_EXIST(103000, "该项目不存在", MessageType.ERROR_MESSAGE),
  PRO_NAME_EXIST(103001, "该项目名称已存在", MessageType.WARN_MESSAGE),
  /** 104xxx 项目模板 */
  TEMPLATE_NOT_EXIST(104000, "该模板不存在", MessageType.ERROR_MESSAGE),
  TEMPLATE_NAME_EXIST(104001, "该模板名称已存在", MessageType.WARN_MESSAGE),
  /** 105xxx 任务步骤 */
  TASK_STEP_NAME_REPEAT(105000, "步骤名称出现重复", MessageType.WARN_MESSAGE),
  /** 106xxx 需求优先级 */
  REQ_PRIORITY_NAME_REPEAT(106000, "优先级名称出现重复", MessageType.WARN_MESSAGE);

  private final Integer code;

  private final String message;

  private final MessageType messageType;

  public void thr() {
    throw new BizException(this);
  }

  public BizException exc() {
    return new BizException(this);
  }
}

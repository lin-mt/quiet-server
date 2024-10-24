package cn.linmt.quiet.enums;

import cn.linmt.quiet.action.DeleteRepositoryIssue;
import cn.linmt.quiet.modal.project.AutomationActionHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** 自动化操作 */
@Getter
@RequiredArgsConstructor
public enum AutomationAction {

  /** 创建分支 */
  CREATE_BRANCH(true, null),
  /** 删除分支 */
  DELETE_BRANCH(true, null),
  /** 创建 PR */
  CREATE_PR(true, null),
  /** 关闭 PR */
  CLOSE_PR(true, null),
  /** 删除 PR */
  DELETE_PR(true, null),
  /** 创建issue */
  CREATE_ISSUE(true, null),
  /** 关闭issue */
  CLOSE_ISSUE(true, null),
  /** 删除issue */
  DELETE_ISSUE(true, DeleteRepositoryIssue.class),
  /** 发送邮件 */
  SEND_EMAIL(false, null),
  /** 发送飞书通知 */
  FEI_SHU_NOTIFY(false, null),
  /** 发送钉钉通知 */
  DING_DING_NOTIFY(false, null),
  /** 发送企业微信通知 */
  WORK_WEI_XIN_NOTIFY(false, null),
  /** 发送站内信 */
  INTERNAL_MESSAGE(false, null);

  private final boolean relateRepository;

  private final Class<? extends AutomationActionHandler<?>> handler;
}

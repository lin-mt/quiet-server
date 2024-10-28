package cn.linmt.quiet.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** 触发自动化的操作 */
@Getter
@RequiredArgsConstructor
public enum TriggerAction {

  /** 开始迭代 */
  START_ITERATION,
  /** 结束迭代 */
  END_ITERATION,
  /** 创建需求 */
  CREATE_REQUIREMENT(true, false, false, false),
  /** 更新需求 */
  UPDATE_REQUIREMENT(true, false, false, false),
  /** 需求状态变更 */
  UPDATE_REQUIREMENT_STATUS(true, true, false, false),
  /** 删除需求 */
  DELETE_REQUIREMENT(true, false, false, false),
  /** 创建任务 */
  CREATE_TASK(false, false, true, false),
  /** 更新任务 */
  UPDATE_TASK(false, false, true, false),
  /** 更新任务步骤 */
  UPDATE_TASK_STEP(false, false, true, true),
  /** 删除任务 */
  DELETE_TASK(false, false, true, false),
  ;

  private final boolean requireRequirementType;
  private final boolean requireRequirementStatus;
  private final boolean requireTaskType;
  private final boolean requireTaskStep;

  TriggerAction() {
    this(false, false, false, false);
  }
}

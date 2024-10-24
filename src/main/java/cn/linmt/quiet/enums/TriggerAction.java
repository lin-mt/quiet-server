package cn.linmt.quiet.enums;

/** 触发自动化的操作 */
public enum TriggerAction {

  /** 开始迭代 */
  START_ITERATION,
  /** 结束迭代 */
  END_ITERATION,
  /** 创建需求 */
  CREATE_REQUIREMENT,
  /** 更新需求 */
  UPDATE_REQUIREMENT,
  /** 需求状态变更 */
  UPDATE_REQUIREMENT_STATUS,
  /** 删除需求 */
  DELETE_REQUIREMENT,
  /** 创建任务 */
  CREATE_TASK,
  /** 更新任务 */
  UPDATE_TASK,
  /** 更新任务步骤 */
  UPDATE_TASK_STEP,
  /** 删除任务 */
  DELETE_TASK,
}

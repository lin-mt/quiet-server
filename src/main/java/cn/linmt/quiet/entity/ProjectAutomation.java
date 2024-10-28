package cn.linmt.quiet.entity;

import cn.linmt.quiet.enums.AutomationAction;
import cn.linmt.quiet.enums.RequirementStatus;
import cn.linmt.quiet.enums.TriggerAction;
import cn.linmt.quiet.modal.jpa.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "project_automation")
public class ProjectAutomation extends BaseEntity {

  @Comment("项目ID")
  @Column(nullable = false)
  private Long projectId;

  @Comment("触发动作")
  @Column(nullable = false)
  private TriggerAction triggerAction;

  @Comment("需求类型ID集合")
  @JdbcTypeCode(SqlTypes.JSON)
  private Set<Long> requirementTypeIds;

  @Comment("任务类型ID集合")
  @JdbcTypeCode(SqlTypes.JSON)
  private Set<Long> taskTypeIds;

  @Comment("前置任务步骤ID")
  private Long preTaskStepId;

  @Comment("后置任务步骤ID")
  private Long afterTaskStepId;

  @Comment("前置需求状态")
  private RequirementStatus preRequirementStatus;

  @Comment("后置需求状态")
  private RequirementStatus afterRequirementStatus;

  @Comment("代码仓库ID")
  @JdbcTypeCode(SqlTypes.JSON)
  private Set<Long> repositoryIds;

  @Comment("自动化动作")
  @Column(nullable = false)
  private AutomationAction automationAction;
}

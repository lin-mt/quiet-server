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
  private Set<Long> requirementTypeId;

  @Comment("任务类型ID集合")
  @JdbcTypeCode(SqlTypes.JSON)
  private Set<Long> taskTypeId;

  @Comment("前置任务步骤ID")
  private Long preStepId;

  @Comment("后置任务步骤ID")
  private Long afterStepId;

  @Comment("前置需求状态")
  private RequirementStatus preStatus;

  @Comment("后置需求状态")
  private RequirementStatus afterStatus;

  @Comment("代码仓库ID")
  private Long repositoryId;

  @Comment("自动化动作")
  @Column(nullable = false)
  private AutomationAction automationAction;
}

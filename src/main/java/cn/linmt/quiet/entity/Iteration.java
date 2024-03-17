package cn.linmt.quiet.entity;

import cn.linmt.quiet.enums.PlanningStatus;
import cn.linmt.quiet.modal.jpa.base.SortableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "iteration")
public class Iteration extends SortableEntity {

  @Comment("迭代名称")
  @Column(name = "name", length = 30, nullable = false)
  private String name;

  @Comment("版本ID")
  @Column(name = "version_id", nullable = false)
  private Long versionId;

  @Comment("迭代状态")
  @Column(name = "status", nullable = false)
  private PlanningStatus status;

  @Comment("计划开始时间")
  @Column(name = "planned_start_time")
  private LocalDateTime plannedStartTime;

  @Comment("计划结束时间")
  @Column(name = "planned_end_time")
  private LocalDateTime plannedEndTime;

  @Comment("实际开始时间")
  @Column(name = "actual_start_time")
  private LocalDateTime actualStartTime;

  @Comment("实际结束时间")
  @Column(name = "actual_end_time")
  private LocalDateTime actualEndTime;

  @Comment("迭代描述")
  @Column(name = "description")
  private String description;
}

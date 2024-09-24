package cn.linmt.quiet.entity;

import cn.linmt.quiet.enums.PlanningStatus;
import cn.linmt.quiet.modal.jpa.base.ParentAndSortableEntity;
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
@Table(name = "version")
public class Version extends ParentAndSortableEntity {

  @Comment("版本名称")
  @Column(length = 30, nullable = false)
  private String name;

  @Comment("项目ID")
  @Column(nullable = false)
  private Long projectId;

  @Comment("版本状态")
  @Column(nullable = false)
  private PlanningStatus status;

  @Comment("计划开始时间")
  private LocalDateTime plannedStartTime;

  @Comment("计划结束时间")
  private LocalDateTime plannedEndTime;

  @Comment("实际开始时间")
  private LocalDateTime actualStartTime;

  @Comment("实际结束时间")
  private LocalDateTime actualEndTime;

  @Comment("版本描述")
  private String description;
}

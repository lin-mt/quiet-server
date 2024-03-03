package cn.linmt.quiet.entity;

import cn.linmt.quiet.enums.BuildTool;
import cn.linmt.quiet.modal.jpa.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "project")
public class Project extends BaseEntity {

  @Comment("项目名称")
  @Column(name = "p_name", length = 50, nullable = false)
  private String name;

  @Comment("构建工具")
  @Column(name = "build_tool", nullable = false)
  private BuildTool buildTool;

  @Comment("git地址")
  @Column(name = "git_address", nullable = false)
  private String gitAddress;

  @Comment("项目描述")
  @Column(name = "description")
  private String description;
}

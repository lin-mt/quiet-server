package cn.linmt.quiet.entity;

import cn.linmt.quiet.modal.jpa.base.ParentAndSortableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
@Table(name = "api_docs_group")
public class ApiDocsGroup extends ParentAndSortableEntity {

  @NotBlank
  @Comment("分组名称")
  @Column(name = "name", length = 30, nullable = false)
  private String name;

  @NotNull
  @Comment("项目ID")
  @Column(name = "project_id", nullable = false)
  private Long projectId;

  @Length(max = 255)
  @Comment("项目描述")
  @Column(name = "description")
  private String description;
}

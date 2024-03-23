package cn.linmt.quiet.entity;

import cn.linmt.quiet.enums.DictionaryType;
import cn.linmt.quiet.modal.jpa.base.ParentAndSortableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "dictionary")
public class Dictionary extends ParentAndSortableEntity {

  @NotBlank
  @Comment("字典名称")
  @Column(name = "name", nullable = false, length = 30)
  private String name;

  @NotBlank
  @Comment("字典编码")
  @Column(name = "code", nullable = false, length = 16)
  private String code;

  @NotNull
  @Comment("字典类型")
  @Column(name = "type", nullable = false)
  private DictionaryType type;
}

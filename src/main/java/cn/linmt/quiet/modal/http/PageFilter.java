package cn.linmt.quiet.modal.http;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@Schema(description = "分页查询")
public class PageFilter {

  @Min(1)
  @Schema(description = "页数")
  private int current;

  @Min(1)
  @Schema(description = "分页大小")
  private int pageSize;

  public Pageable pageable() {
    return PageRequest.of(current - 1, pageSize);
  }
}

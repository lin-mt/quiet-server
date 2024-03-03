package cn.linmt.quiet.controller.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDTO {

  @NotBlank
  @Length(max = 16)
  @Schema(title = "用户名", example = "admin")
  private String username;

  @NotBlank
  @Length(max = 32)
  @Schema(title = "密码", example = "hVr37Kqc5C9xkkHaNmEe")
  private String password;
}

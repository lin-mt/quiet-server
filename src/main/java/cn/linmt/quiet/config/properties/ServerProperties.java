package cn.linmt.quiet.config.properties;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "quiet")
public class ServerProperties {

  /** 管理员用户名 */
  private final String adminUsername;

  /** 管理员密码 */
  private final String adminPassword;

  /** 加密密钥 */
  private final String secretKey;

  /** 是否是开发环境 */
  private final Boolean dev;
}

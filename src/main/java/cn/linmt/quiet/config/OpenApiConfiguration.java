package cn.linmt.quiet.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

  static {
    SpringDocUtils.getConfig().replaceWithSchema(Long.class, new StringSchema());
  }

  @Bean
  public OpenAPI customizeOpenAPI() {
    final String securitySchemeName =
        "eyJhbGciOiJub25lIn0.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwNzk2OTUyNywiZXhwIjoxNzEwNTYxNTI3fQ.";
    return new OpenAPI()
        .specVersion(SpecVersion.V31)
        .info(new Info().title("Quiet").version("1.0.0"))
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(
            new Components()
                .addSecuritySchemes(
                    securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
  }
}

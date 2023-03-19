package com.kakao.blog.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Blog Service API", description = "Blog Service API Description", version = "v1"))
@Configuration
public class SwaggerConfig {

    private static final String[] packagesToScan = {"com.kakao.blog.controller"};

    @Bean
    public GroupedOpenApi openApi() {
        String[] paths = {"/health", "/api/v1/blog/**"};
        return GroupedOpenApi.builder()
          .group("Blog Service API")
          .pathsToMatch(paths)
          .packagesToScan(packagesToScan)
          .addOpenApiCustomiser(customizeOpenApi())
          .build();
    }

    public OpenApiCustomiser customizeOpenApi() {
        return OpenApi -> OpenApi
          .externalDocs(new ExternalDocumentation());
    }

}

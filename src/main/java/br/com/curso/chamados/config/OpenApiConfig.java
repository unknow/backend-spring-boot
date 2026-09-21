package br.com.curso.chamados.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String ESQUEMA = "bearer-jwt";

    @Bean
    OpenAPI apiDoSistemaDeChamados() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Chamados")
                        .version("v1")
                        .description("API de abertura e acompanhamento de chamados. "
                                + "Endpoints protegidos exigem um token do Keycloak: "
                                + "clique em Authorize e cole o access_token."))
                .components(new Components().addSecuritySchemes(ESQUEMA,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList(ESQUEMA));
    }
}

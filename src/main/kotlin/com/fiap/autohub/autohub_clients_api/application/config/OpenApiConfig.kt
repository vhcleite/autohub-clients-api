package com.fiap.autohub.autohub_clients_api.application.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "AutoHub Clients API",
        version = "v1.0", // Versão da sua API
        description = "API para gerenciamento de perfis de clientes da plataforma AutoHub."
    )
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Insira o Token JWT obtido no login do Cognito aqui."
)
class OpenApiConfig {
    
}
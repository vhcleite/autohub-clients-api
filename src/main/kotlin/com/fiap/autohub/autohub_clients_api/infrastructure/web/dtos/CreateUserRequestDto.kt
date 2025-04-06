package com.fiap.autohub.autohub_clients_api.infrastructure.web.dtos

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Schema(description = "Dados básicos para criação inicial do perfil do usuário após autenticação no Cognito.")
data class CreateUserRequestDto(

    @field:NotBlank(message = "First name cannot be blank")
    @Schema(description = "Primeiro nome do usuário", example = "Maria", requiredMode = Schema.RequiredMode.REQUIRED)
    val firstName: String?,

    @field:NotBlank(message = "Last name cannot be blank")
    @Schema(description = "Sobrenome do usuário", example = "Souza", requiredMode = Schema.RequiredMode.REQUIRED)
    val lastName: String?,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Invalid email format")
    @Schema(
        description = "Endereço de e-mail principal do usuário (deve coincidir com o usado no Cognito)",
        example = "maria.souza@example.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val email: String?
)
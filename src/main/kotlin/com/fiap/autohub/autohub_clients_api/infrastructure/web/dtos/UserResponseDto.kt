// Exemplo em UserResponseDto.kt
package com.fiap.autohub.autohub_clients_api.infrastructure.web.dtos

import io.swagger.v3.oas.annotations.media.Schema // Importar Schema

@Schema(description = "Representa os dados de resposta de um usuário")
data class UserResponseDto(
    @Schema(description = "ID único do usuário (Cognito Sub)", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    val id: String,

    @Schema(description = "Primeiro nome do usuário", example = "João")
    val firstName: String,

    @Schema(description = "Sobrenome do usuário", example = "Silva")
    val lastName: String,

    @Schema(description = "Endereço de e-mail do usuário", example = "joao.silva@example.com")
    val email: String,

    @Schema(
        description = "CPF do usuário (formatado ou não)",
        example = "111.222.333-44",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    val cpf: String?,

    @Schema(
        description = "Número da CNH do usuário",
        example = "98765432101",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    val cnh: String?,

    @Schema(description = "Endereço do usuário", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    val address: AddressDto?, // AddressDto também pode ser anotado similarmente

    @Schema(description = "Data e hora de criação do registro (ISO 8601 UTC)", example = "2025-04-06T00:25:10.123Z")
    val createdAt: String,

    @Schema(description = "Data e hora da última atualização (ISO 8601 UTC)", example = "2025-04-06T00:30:00.456Z")
    val updatedAt: String
)

// Faça o mesmo para AddressDto, CreateUserRequestDto, UpdateUserRequestDto e seus campos.
// Use requiredMode = Schema.RequiredMode.REQUIRED para campos obrigatórios no DTO.
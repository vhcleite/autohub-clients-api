package com.fiap.autohub.autohub_clients_api.infrastructure.web.dtos

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

@Schema(description = "Dados para atualização/complemento do perfil do usuário autenticado.")
data class UpdateUserRequestDto(
    @Schema(
        description = "Primeiro nome atualizado do usuário (opcional para envio, pode ser nulo se não for alterar)",
        example = "João Carlos",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    val firstName: String?,

    @Schema(
        description = "Sobrenome atualizado do usuário (opcional para envio, pode ser nulo se não for alterar)",
        example = "Silva Santos",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    val lastName: String?,

    @field:NotBlank(message = "CPF cannot be blank")
    @Schema(
        description = "CPF do usuário (obrigatório nesta atualização)",
        example = "111.222.333-44",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val cpf: String?,

    @field:NotBlank(message = "CNH cannot be blank")
    @Schema(
        description = "Número da CNH do usuário (obrigatório nesta atualização)",
        example = "98765432101",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val cnh: String?,

    @field:Valid
    @Schema(
        description = "Endereço completo atualizado do usuário (opcional para envio, mas campos internos podem ser obrigatórios se o objeto for enviado)",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    val address: AddressDto?
)
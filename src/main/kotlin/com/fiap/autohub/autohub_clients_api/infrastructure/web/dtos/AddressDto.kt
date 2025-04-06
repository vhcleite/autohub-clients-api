package com.fiap.autohub.autohub_clients_api.infrastructure.web.dtos

import io.swagger.v3.oas.annotations.media.Schema // Importar Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "Representa os dados de endereço do usuário") // Descrição do DTO
data class AddressDto(

    @field:NotBlank(message = "Street cannot be blank")
    @Schema(
        description = "Nome da rua/logradouro",
        example = "Avenida Principal",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val street: String?,

    @field:NotBlank(message = "Number cannot be blank")
    @Schema(description = "Número do imóvel", example = "123 B", requiredMode = Schema.RequiredMode.REQUIRED)
    val number: String?,

    @Schema(
        description = "Complemento do endereço (bloco, apto, etc.)",
        example = "Apto 101",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    val complement: String?,

    @field:NotBlank(message = "Neighborhood cannot be blank")
    @Schema(description = "Bairro", example = "Centro", requiredMode = Schema.RequiredMode.REQUIRED)
    val neighborhood: String?,

    @field:NotBlank(message = "City cannot be blank")
    @Schema(description = "Cidade", example = "Diadema", requiredMode = Schema.RequiredMode.REQUIRED)
    val city: String?,

    @field:NotBlank(message = "State cannot be blank")
    @field:Size(min = 2, max = 2, message = "State must be 2 characters")
    @Schema(description = "Sigla do estado (UF)", example = "SP", requiredMode = Schema.RequiredMode.REQUIRED)
    val state: String?,

    @field:NotBlank(message = "Zip code cannot be blank")
    @Schema(
        description = "Código de Endereçamento Postal (CEP)",
        example = "09910-170",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val zipCode: String?
)
package com.fiap.autohub.autohub_clients_api.infrastructure.web.controllers

import com.fiap.autohub.autohub_clients_api.domain.commands.UserUpdateCommand
import com.fiap.autohub.autohub_clients_api.domain.ports.`in`.UserServicePort
import com.fiap.autohub.autohub_clients_api.infrastructure.web.dtos.CreateUserRequestDto
import com.fiap.autohub.autohub_clients_api.infrastructure.web.dtos.UpdateUserRequestDto
import com.fiap.autohub.autohub_clients_api.infrastructure.web.dtos.UserResponseDto
import com.fiap.autohub.autohub_clients_api.infrastructure.web.mappers.UserDtoMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/users")
@Tag(
    name = "User Management",
    description = "Endpoints para gerenciamento de perfis de usuário"
)
class UserController(
    private val userService: UserServicePort,
    private val mapper: UserDtoMapper
) {

    @PostMapping("/basic")
    @Operation(
        summary = "Cria registro inicial do usuário",
        description = "Cria o perfil básico do usuário no banco de dados após autenticação inicial no Cognito."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Usuário criado com sucesso",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserResponseDto::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Requisição inválida (ex: dados faltando)",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Não autorizado (token JWT inválido ou ausente)",
                content = [Content()]
            )
        ]
    )
    @SecurityRequirement(name = "bearerAuth")
    fun createInitialUser(
        @Valid @RequestBody request: CreateUserRequestDto,
        @Parameter(hidden = true)
        principal: Principal
    ): ResponseEntity<UserResponseDto> {
        val userId = principal.name
        val createCommand = mapper.toCreateCommand(request)
        val createdUser = userService.createInitialUser(userId, createCommand)
        val responseDto = mapper.toResponseDto(createdUser)
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto)
    }

    @PutMapping("/me")
    @Operation(
        summary = "Atualiza dados do usuário logado",
        description = "Permite ao usuário autenticado atualizar/complementar seus dados cadastrais (CPF, CNH, Endereço, etc.)."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Dados atualizados com sucesso",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserResponseDto::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Requisição inválida (ex: dados inválidos)",
                content = [Content()]
            ),
            ApiResponse(responseCode = "401", description = "Não autorizado", content = [Content()]),
            ApiResponse(
                responseCode = "404",
                description = "Usuário não encontrado (raro para /me)",
                content = [Content()]
            )
        ]
    )
    @SecurityRequirement(name = "bearerAuth")
    fun updateCurrentUser(
        @Valid @RequestBody request: UpdateUserRequestDto,
        @Parameter(hidden = true) principal: Principal
    ): ResponseEntity<UserResponseDto> {
        val userId = principal.name
        val command: UserUpdateCommand = mapper.toUpdateCommand(request)
        val updatedUser = userService.updateUser(userId, command)
        val responseDto = mapper.toResponseDto(updatedUser)
        return ResponseEntity.ok(responseDto)
    }

    @GetMapping("/me")
    @Operation(
        summary = "Obtém dados do usuário logado",
        description = "Retorna os dados cadastrais completos do usuário autenticado."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Dados do usuário encontrados",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserResponseDto::class)
                )]
            ),
            ApiResponse(responseCode = "401", description = "Não autorizado", content = [Content()]),
            ApiResponse(
                responseCode = "404",
                description = "Usuário não encontrado no banco de dados",
                content = [Content()]
            )
        ]
    )
    @SecurityRequirement(name = "bearerAuth")
    fun getCurrentUser(
        @Parameter(hidden = true) principal: Principal
    ): ResponseEntity<UserResponseDto> {
        val userId = principal.name
        val user = userService.findUserById(userId)
        return user?.let { ResponseEntity.ok(mapper.toResponseDto(it)) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtém dados de um usuário por ID",
        description = "Retorna os dados de um usuário específico. Requer permissão adequada (ex: admin)."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Dados do usuário encontrados",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserResponseDto::class)
                )]
            ),
            ApiResponse(responseCode = "401", description = "Não autorizado", content = [Content()]),
            ApiResponse(
                responseCode = "403",
                description = "Proibido (sem permissão para acessar este usuário)",
                content = [Content()]
            ),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = [Content()])
        ]
    )
    @SecurityRequirement(name = "bearerAuth")
    fun getUserById(
        @Parameter(description = "ID (sub Cognito) do usuário a ser buscado") // Descreve o path variable
        @PathVariable id: String
    ): ResponseEntity<UserResponseDto> {
        // TODO de autorização aqui!
        val user = userService.findUserById(id)
        return user?.let { ResponseEntity.ok(mapper.toResponseDto(it)) }
            ?: ResponseEntity.notFound().build()
    }


    @DeleteMapping("/me")
    @Operation(
        summary = "Deleta o usuário logado",
        description = "Remove o registro do usuário autenticado do banco de dados."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso", content = [Content()]),
            ApiResponse(responseCode = "401", description = "Não autorizado", content = [Content()]),
            ApiResponse(
                responseCode = "404",
                description = "Usuário não encontrado (raro para /me)",
                content = [Content()]
            )
        ]
    )
    @SecurityRequirement(name = "bearerAuth")
    fun deleteCurrentUser(
        @Parameter(hidden = true) principal: Principal
    ): ResponseEntity<Void> {
        val userId = principal.name
        userService.deleteUserById(userId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deleta um usuário por ID",
        description = "Remove o registro de um usuário específico. Requer permissão adequada (ex: admin)."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso", content = [Content()]),
            ApiResponse(responseCode = "401", description = "Não autorizado", content = [Content()]),
            ApiResponse(
                responseCode = "403",
                description = "Proibido (sem permissão para deletar este usuário)",
                content = [Content()]
            ),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = [Content()])
        ]
    )
    @SecurityRequirement(name = "bearerAuth")
    fun deleteUserById(
        @Parameter(description = "ID (sub Cognito) do usuário a ser deletado")
        @PathVariable id: String
    ): ResponseEntity<Void> {
        // TODO de autorização aqui!
        userService.deleteUserById(id)
        return ResponseEntity.noContent().build()
    }
}
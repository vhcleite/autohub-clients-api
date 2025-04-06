package com.fiap.autohub.autohub_clients_api.infrastructure.web.mappers


import com.fiap.autohub.autohub_clients_api.domain.commands.AddressUpdateCommand
import com.fiap.autohub.autohub_clients_api.domain.commands.UserCreateCommand
import com.fiap.autohub.autohub_clients_api.domain.commands.UserUpdateCommand
import com.fiap.autohub.autohub_clients_api.domain.entities.User
import com.fiap.autohub.autohub_clients_api.infrastructure.web.dtos.AddressDto
import com.fiap.autohub.autohub_clients_api.infrastructure.web.dtos.CreateUserRequestDto
import com.fiap.autohub.autohub_clients_api.infrastructure.web.dtos.UpdateUserRequestDto
import com.fiap.autohub.autohub_clients_api.infrastructure.web.dtos.UserResponseDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface UserDtoMapper {

    @Mappings(
        Mapping(
            target = "createdAt",
            expression = "java(user.getCreatedAt().toString())"
        ),
        Mapping(
            target = "updatedAt",
            expression = "java(user.getUpdatedAt().toString())"
        )
    )
    fun toResponseDto(user: User): UserResponseDto
    fun toUpdateCommand(dto: UpdateUserRequestDto): UserUpdateCommand
    fun toUpdateCommand(dto: AddressDto?): AddressUpdateCommand?
    fun toCreateCommand(dto: CreateUserRequestDto): UserCreateCommand

}

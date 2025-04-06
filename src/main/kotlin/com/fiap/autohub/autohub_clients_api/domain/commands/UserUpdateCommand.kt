package com.fiap.autohub.autohub_clients_api.domain.commands

data class UserUpdateCommand(
    val firstName: String?,
    val lastName: String?,
    val cpf: String?,
    val cnh: String?,
    val address: AddressUpdateCommand?
)

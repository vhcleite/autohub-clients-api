package com.fiap.autohub.autohub_clients_api.domain.commands

data class UserCreateCommand(
    val firstName: String,
    val lastName: String,
    val email: String,
)

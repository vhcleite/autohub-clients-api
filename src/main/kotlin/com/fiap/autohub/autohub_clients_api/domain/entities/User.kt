package com.fiap.autohub.autohub_clients_api.domain.entities

import java.time.OffsetDateTime

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val cpf: String? = null,
    val cnh: String? = null,
    val address: Address? = null,
    val createdAt: OffsetDateTime? = null,
    val updatedAt: OffsetDateTime? = null,
)
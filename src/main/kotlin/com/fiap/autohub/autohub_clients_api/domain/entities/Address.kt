package com.fiap.autohub.autohub_clients_api.domain.entities

data class Address(
    val street: String,
    val number: String,
    val complement: String?,
    val neighborhood: String,
    val city: String,
    val state: String,
    val zipCode: String
)
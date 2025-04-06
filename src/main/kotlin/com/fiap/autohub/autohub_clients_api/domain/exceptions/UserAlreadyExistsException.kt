package com.fiap.autohub.autohub_clients_api.domain.exceptions

class UserAlreadyExistsException(id: String) : RuntimeException("User already exists with id: $id")

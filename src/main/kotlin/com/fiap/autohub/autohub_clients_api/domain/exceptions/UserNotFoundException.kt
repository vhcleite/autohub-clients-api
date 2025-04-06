package com.fiap.autohub.autohub_clients_api.domain.exceptions

class UserNotFoundException(id: String) : RuntimeException("User not found with id: $id")

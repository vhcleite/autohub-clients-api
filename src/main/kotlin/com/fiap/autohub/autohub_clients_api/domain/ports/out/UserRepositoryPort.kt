package com.fiap.autohub.autohub_clients_api.domain.ports.out

import com.fiap.autohub.autohub_clients_api.domain.entities.User

interface UserRepositoryPort {
    fun save(user: User): User
    fun findById(id: String): User?
    fun deleteById(id: String)
}
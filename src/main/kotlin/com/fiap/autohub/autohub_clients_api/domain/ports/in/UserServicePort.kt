package com.fiap.autohub.autohub_clients_api.domain.ports.`in`

import com.fiap.autohub.autohub_clients_api.domain.commands.UserCreateCommand
import com.fiap.autohub.autohub_clients_api.domain.commands.UserUpdateCommand
import com.fiap.autohub.autohub_clients_api.domain.entities.User

interface UserServicePort {
    fun createInitialUser(userId: String, userCreateCommand: UserCreateCommand): User
    fun updateUser(userId: String, command: UserUpdateCommand): User
    fun findUserById(id: String): User?
    fun deleteUserById(id: String)
}
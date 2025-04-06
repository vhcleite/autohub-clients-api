package com.fiap.autohub.autohub_clients_api.domain.services

import com.fiap.autohub.autohub_clients_api.domain.commands.AddressUpdateCommand
import com.fiap.autohub.autohub_clients_api.domain.commands.UserCreateCommand
import com.fiap.autohub.autohub_clients_api.domain.commands.UserUpdateCommand
import com.fiap.autohub.autohub_clients_api.domain.entities.Address
import com.fiap.autohub.autohub_clients_api.domain.entities.User
import com.fiap.autohub.autohub_clients_api.domain.exceptions.UserAlreadyExistsException
import com.fiap.autohub.autohub_clients_api.domain.exceptions.UserNotFoundException
import com.fiap.autohub.autohub_clients_api.domain.ports.`in`.UserServicePort
import com.fiap.autohub.autohub_clients_api.domain.ports.out.UserRepositoryPort
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Service
class UserServiceImpl(
    private val userRepository: UserRepositoryPort
) : UserServicePort {

    override fun createInitialUser(userId: String, userCreateCommand: UserCreateCommand): User {
        val existingUser = userRepository.findById(userId)
        if (existingUser != null) throw UserAlreadyExistsException(userId)

        val now = OffsetDateTime.now(ZoneOffset.UTC)
        val userToSave = User(
            id = userId,
            firstName = userCreateCommand.firstName,
            lastName = userCreateCommand.lastName,
            email = userCreateCommand.email,
            createdAt = now,
            updatedAt = now,
        )
        return userRepository.save(userToSave)
    }

    override fun updateUser(userId: String, command: UserUpdateCommand): User {
        val existingUser = userRepository.findById(userId)
            ?: throw UserNotFoundException(userId)

        val updatedUser = mapUpdateUserCommand(existingUser, command)
        return userRepository.save(updatedUser)
    }

    private fun mapUpdateUserCommand(
        existingUser: User,
        command: UserUpdateCommand
    ): User {
        val updatedUser = existingUser.copy(
            firstName = command.firstName ?: existingUser.firstName,
            lastName = command.lastName ?: existingUser.lastName,
            cpf = command.cpf ?: existingUser.cpf,
            cnh = command.cnh ?: existingUser.cnh,
            address = mapUpdateAddressCommand(
                command.address,
                existingUser.address
            ),
            updatedAt = OffsetDateTime.now(ZoneOffset.UTC)
        )
        return updatedUser
    }

    private fun mapUpdateAddressCommand(command: AddressUpdateCommand?, existingAddress: Address?): Address? {
        if (command == null) return existingAddress

        return Address(
            street = command.street,
            number = command.number,
            complement = command.complement,
            neighborhood = command.neighborhood,
            city = command.city,
            state = command.state,
            zipCode = command.zipCode
        )
    }

    override fun findUserById(id: String): User? {
        return userRepository.findById(id)
    }

    override fun deleteUserById(id: String) {
        userRepository.deleteById(id)
    }
}

package com.fiap.autohub.autohub_clients_api.infrastructure.persistence.mappers


import com.fiap.autohub.autohub_clients_api.domain.entities.Address
import com.fiap.autohub.autohub_clients_api.domain.entities.User
import com.fiap.autohub.autohub_clients_api.infrastructure.persistence.entities.AddressPersistenceEntity
import com.fiap.autohub.autohub_clients_api.infrastructure.persistence.entities.UserPersistenceEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import org.slf4j.LoggerFactory
import java.time.OffsetDateTime
import java.time.format.DateTimeParseException


@Mapper(componentModel = "spring")
abstract class UserPersistenceMapper {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Mappings(
        Mapping(target = "createdAt", ignore = true),
        Mapping(target = "updatedAt", ignore = true)
    )
    abstract fun toPersistenceEntity(user: User): UserPersistenceEntity
    abstract fun toPersistenceEntity(address: Address?): AddressPersistenceEntity?

    @Mappings(
        Mapping(target = "createdAt", source = "createdAt", qualifiedByName = ["stringToOffsetDateTime"]),
        Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = ["stringToOffsetDateTime"])
    )
    abstract fun toDomainEntity(entity: UserPersistenceEntity): User
    abstract fun toDomainEntity(entity: AddressPersistenceEntity?): Address?

    @Named("stringToOffsetDateTime")
    fun stringToOffsetDateTime(dateTimeString: String?): OffsetDateTime? {
        if (dateTimeString == null) {
            return null
        }
        return try {
            OffsetDateTime.parse(dateTimeString)
        } catch (e: DateTimeParseException) {
            logger.error("Failed to parse timestamp string: {}", dateTimeString, e)
            OffsetDateTime.MIN
        }
    }
}
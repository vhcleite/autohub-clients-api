package com.fiap.autohub.autohub_clients_api.infrastructure.persistence.repositories


import com.fiap.autohub.autohub_clients_api.domain.entities.User
import com.fiap.autohub.autohub_clients_api.domain.ports.out.UserRepositoryPort
import com.fiap.autohub.autohub_clients_api.infrastructure.persistence.entities.UserPersistenceEntity
import com.fiap.autohub.autohub_clients_api.infrastructure.persistence.mappers.UserPersistenceMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest

@Repository
class DynamoDbUserRepositoryAdapter(
    private val enhancedClient: DynamoDbEnhancedClient,
    private val mapper: UserPersistenceMapper,
    @Value("\${dynamodb.table-name}") private val tableName: String
) : UserRepositoryPort {

    private val userTable = enhancedClient.table(
        tableName,
        TableSchema.fromBean(UserPersistenceEntity::class.java)
    )

    override fun save(user: User): User {
        val persistenceEntity = mapper.toPersistenceEntity(user)
        persistenceEntity.createdAt = user.createdAt.toString()
        persistenceEntity.updatedAt = user.updatedAt.toString()

        userTable.putItem(persistenceEntity)
        return mapper.toDomainEntity(persistenceEntity)
    }

    override fun findById(id: String): User? {
        val key = Key.builder().partitionValue(id).build()
        val request = GetItemEnhancedRequest.builder().key(key).build()
        val persistenceEntity = userTable.getItem(request)
        return persistenceEntity?.let { mapper.toDomainEntity(it) }
    }

    override fun deleteById(id: String) {
        val key = Key.builder().partitionValue(id).build()
        userTable.deleteItem(key)
    }
}
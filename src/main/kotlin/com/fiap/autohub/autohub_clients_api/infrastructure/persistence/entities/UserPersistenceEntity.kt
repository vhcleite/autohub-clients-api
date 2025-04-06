package com.fiap.autohub.autohub_clients_api.infrastructure.persistence.entities


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*

@DynamoDbBean
data class UserPersistenceEntity(
    @get:DynamoDbPartitionKey
    var id: String? = null, // Cognito Sub

    var firstName: String? = null,
    var lastName: String? = null,

    @get:DynamoDbSecondaryPartitionKey(indexNames = ["email-index"])
    var email: String? = null,

    var cpf: String? = null,
    var cnh: String? = null,
    @get:DynamoDbFlatten
    var address: AddressPersistenceEntity? = null,

    var createdAt: String? = null,
    var updatedAt: String? = null,
)

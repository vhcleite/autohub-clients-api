package com.fiap.autohub.autohub_clients_api.infrastructure.persistence.entities

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean

@DynamoDbBean
data class AddressPersistenceEntity(
    var street: String? = null,
    var number: String? = null,
    var complement: String? = null,
    var neighborhood: String? = null,
    var city: String? = null,
    var state: String? = null,
    var zipCode: String? = null
)
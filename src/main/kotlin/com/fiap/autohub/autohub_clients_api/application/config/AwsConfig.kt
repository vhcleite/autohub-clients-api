package com.fiap.autohub.autohub_clients_api.application.config


import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

@Configuration
class AwsConfig {

    @Value("\${aws.region}")
    private lateinit var awsRegion: String

    @Bean
    @Profile("!local")
    fun dynamoDbEnhancedClientProd(): DynamoDbEnhancedClient {
        val dynamoDbClient = DynamoDbClient.builder()
            .region(Region.of(awsRegion))
            .build()
        return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build()
    }

    @Bean
    @Profile("local")
    fun dynamoDbEnhancedClientLocal(
        @Value("\${aws.dynamodb.endpoint}") dynamoDbEndpoint: String,
        @Value("\${aws.credentials.accessKey}") accessKey: String,
        @Value("\${aws.credentials.secretKey}") secretKey: String
    ): DynamoDbEnhancedClient {
        val dynamoDbClient = DynamoDbClient.builder()
            .region(Region.of(awsRegion))
            .endpointOverride(URI.create(dynamoDbEndpoint))
            .credentialsProvider(
                StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey))
            )
            .build()
        return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build()
    }
}

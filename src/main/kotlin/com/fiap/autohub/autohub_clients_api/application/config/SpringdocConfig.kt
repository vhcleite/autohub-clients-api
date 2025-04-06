package com.fiap.autohub.autohub_clients_api.application.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.jackson.ModelResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SpringdocConfig {

    @Bean
    fun modelResolver(objectMapper: ObjectMapper?): ModelResolver {
        return ModelResolver(objectMapper)
    }
}
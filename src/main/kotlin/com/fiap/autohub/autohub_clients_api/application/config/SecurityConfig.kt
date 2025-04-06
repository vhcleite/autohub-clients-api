package com.fiap.autohub.autohub_clients_api.application.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.configurationSource(corsConfigurationSource()) }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt { } }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(
                        "/actuator/health",
                        "/swagger-ui.html",      // A página principal da UI
                        "/swagger-ui/**",       // Recursos da UI (CSS, JS, etc.)
                        "/v3/api-docs",         // O endpoint principal da especificação OpenAPI v3 (JSON)
                        "/v3/api-docs/**",       // Recursos adicionais da especificação (ex: YAML)
                        "/swagger-resources/**" // Pode ser necessário para compatibi
                    ).permitAll()

                    .anyRequest().authenticated()
            }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")// insecure for production
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
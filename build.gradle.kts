import org.springframework.boot.gradle.plugin.SpringBootPlugin


plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("kapt") version "1.9.23"
}

group = "com.fiap.autohub"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom(SpringBootPlugin.BOM_COORDINATES)
        // BOM do Spring Cloud - Necessário para Spring Cloud Function
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.1")
        // BOM do AWS SDK v2
        mavenBom("software.amazon.awssdk:bom:2.25.18")
    }
}

dependencies {

    // --- Spring Boot Starters ---
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")

// Habilita a funcionalidade de Resource Server para OAuth2 (valida JWTs)
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // --- Kotlin ---
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // --- AWS SDK v2 ---
    implementation("software.amazon.awssdk:dynamodb-enhanced") // Cliente avançado para DynamoDB

    // --- MapStruct (Mapeamento) ---
    implementation("org.mapstruct:mapstruct:1.5.5.Final") // Biblioteca MapStruct
    kapt("org.mapstruct:mapstruct-processor:1.5.5.Final") // Processador de anotações para gerar código

    //SWAGGER
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")


    // --- Testes ---
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        // Excluir JUnit 4 se houver conflitos (JUnit 5 é o padrão)
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5") // Suporte Kotlin para JUnit 5
    // ---tests---
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    archiveFileName.set("${project.name}-${project.version}.jar")
}

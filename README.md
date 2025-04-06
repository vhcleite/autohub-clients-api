# AutoHub Clients API

API responsável pelo gerenciamento de perfis de clientes da plataforma de revenda de veículos AutoHub.

## Descrição

Este microserviço fornece endpoints RESTful para criar, atualizar, consultar e deletar informações de perfil de
clientes. A autenticação é gerenciada pelo AWS Cognito e os dados são persistidos no AWS DynamoDB.

**Tecnologias Principais:**

* Kotlin
* Spring Boot 3.x
* Gradle
* Arquitetura Hexagonal (Ports & Adapters)
* AWS DynamoDB
* AWS Cognito (para autenticação - gerenciado separadamente)
* Docker / Docker Compose / LocalStack (para ambiente de desenvolvimento local)
* Springdoc OpenAPI (Swagger) (para documentação da API)

## Pré-requisitos

Antes de começar, garanta que você tenha as seguintes ferramentas instaladas:

* **JDK 21**
* **Docker e Docker Compose**

## Configuração do Ambiente de Desenvolvimento

1. **Iniciar Ambiente Local (LocalStack + DynamoDB):**
    * Este projeto usa Docker Compose para iniciar o LocalStack, que simula o DynamoDB localmente. Um script de
      inicialização cria a tabela automaticamente.
    * Execute na raiz do projeto:
        ```bash
        docker-compose up -d
        ```

2. **Configuração da Aplicação:**
    * As configurações principais estão em `src/main/resources/application.yml`.
    * Configurações específicas para o ambiente local (como o endpoint do LocalStack) estão em
      `src/main/resources/application-local.yml`.
    * O perfil `local` é ativado por padrão se a variável de ambiente `ENVIRONMENT` não estiver definida.

## Rodando a Aplicação Localmente

1. **Via IDE:**
    * Importe o projeto como um projeto Gradle.
    * Configure a execução da classe principal (`AutohubClientsApiApplication.kt` ou similar).
    * **Importante:** Certifique-se de que o perfil Spring `local` esteja ativo nas configurações de execução da sua
      IDE.
    * Execute a aplicação.

2. **Via Gradle:**
    * Execute no terminal, na raiz do projeto:
        ```bash
        ./gradlew bootRun --args='--spring.profiles.active=local'
        ```
    * A API estará disponível por padrão em `http://localhost:8080`.

## Testando a API Localmente

1. **Documentação (Swagger UI):**
    * Com a aplicação rodando, acesse: `http://localhost:8080/swagger-ui.html`
    * Use o botão "Authorize" para inserir um token JWT válido do Cognito (obtido via fluxo de login) para testar os
      endpoints protegidos.

2. **Obtendo um Token JWT de Teste:**
    * Use a Hosted UI do Cognito configurada no Terraform (`cognito_hosted_ui_domain` output).
    * Faça login com um usuário de teste (cadastrado e confirmado).
    * Use o fluxo "Implicit Grant" (habilitado no `app_client` via Terraform) com `redirect_uri=https://jwt.io` para
      visualizar/copiar o `id_token` ou `access_token` gerado. **Lembre-se que Implicit Grant não é recomendado para
      produção.**

## Documentação da API

A documentação interativa (Swagger UI) está disponível no endpoint `/swagger-ui.html` da API rodando (localmente ou na
AWS).

Ex: `http://localhost:8080/swagger-ui.html` ou `https://<api-gw-url>/swagger-ui.html`

---


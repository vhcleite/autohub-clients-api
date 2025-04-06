#!/bin/sh
echo "Starting aws services initialization..."
# Espera um pouco para garantir que o serviço DynamoDB esteja pronto (opcional, mas pode ajudar)
sleep 5

# Cria a tabela DynamoDB
# - Removido --endpoint-url: awslocal geralmente funciona sem ele dentro do container
# - Mudado para PAY_PER_REQUEST para consistência com o Terraform (se desejar)
awslocal dynamodb create-table \
    --table-name VehicleResaleUsers-Local \
    --attribute-definitions AttributeName=id,AttributeType=S \
    --key-schema AttributeName=id,KeyType=HASH \
    --billing-mode PAY_PER_REQUEST \

echo "Initialization complete!"
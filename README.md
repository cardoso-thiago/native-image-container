# Teste com geração de binário nativo com DynamoDB

- Comando para criar a tabela:

```shell
aws dynamodb create-table \
--table-name movie_details \
--attribute-definitions \
AttributeName=id,AttributeType=S \
--key-schema \
AttributeName=id,KeyType=HASH \
--provisioned-throughput \
ReadCapacityUnits=1,WriteCapacityUnits=1 \
--output json \
--endpoint-url http://localhost:4566
```

- Adicionar um novo filme:

```shell
curl -X POST http://localhost:8081/movie \
-H "Content-Type: application/json" \
-d '{
"id": "1",
"title": "Interstellar",
"year": "2014-11-07",
"genre": "Sci-Fi",
"country": "USA",
"duration": 169,
"language": "English"
}'
```

- Consultar o filme adicionado:

```shell
curl http://localhost:8081/movie/1
```
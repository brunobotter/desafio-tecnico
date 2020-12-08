# Desafio técnico para vaga de emprego

# Criação de um CRUD, com arquitetura REST, utilizando Postgresql como banco de dados

## Tecnologias utilizadas

- spring data jpa
- spring boot starter web
- spring boot devtools
- spring-hateoas
- hibernate-validator
- postgresql

## Tabela Pessoa

|  ID	| Nome 	|
|---	|---	|
|  1 	|bruno 	|
|  2	|joao  	|


## Endpoints

### GET buscaTodosPorNomeCliente
#### Com 3 parametros opcionais, nome, page com default 0 e limite com default 20
#### http://localhost:8080/api/pessoa/busca/?nome=&page=0&limite=20
#### Retornando um Status 200 OK

```json
"_embedded": {
        "pessoaList": [
            {
                "id": 1,
                "nome": "bruno"
            },
            {
                "id": 2,
                "nome": "joao"
            },
            {
                "id": 3,
                "nome": "bruno soares"
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/pessoa/busca/?page=0&size=20"
        }
    },
    "page": {
        "size": 2,
        "totalElements": 3,
        "totalPages": 1,
        "number": 0
    }
}
```

### GET buscaPorId
#### Com 1 parametro obrigatorio id
#### http://localhost:8080/api/pessoa/id
#### Retornando um Status 200 OK
#### JSON RETORNADO

```
{
    "id": 33,
    "nome": "joao victor menezes"
}
```

### DELETE deleta
#### Com 1 parametro obrigatorio id
#### http://localhost:8080/api/pessoa/id
#### Retornando um Status 204 No Content


### POST adiciona
#### Passando um request body
#### http://localhost:8080/api/pessoa
#### Retornando um Status 201 Created
#### JSON RETORNADO

```
{
    "id": 1,
    "nome": "joao victor menezes"
}
```

### PUT atualiza
#### Passando um request body e um parametro obrigatorio id
#### http://localhost:8080/api/pessoa/id
#### Retornando um Status 200 OK
#### JSON RETORNADO

```
{
    "id": 1,
    "nome": "joao victor menezes"
}
```

## Erros

### Tamanho do campo inferior a 2 caracteres ou superior a 100 caracteres
### Retornado Status 400 BAD REQUEST

```
{
    "status": 400,
    "dataHora": "2020-12-08T16:09:30.041846-03:00",
    "erro": "Um ou mais campos estão invalidos. Faça o preenchimento correto!",
    "campos": [
        {
            "nome": "nome",
            "mensagem": "Deve conter entre 2 e 100 caracteres"
        }
    ]
}
```

### Inserindo um objeto nulo no campo nome
### Retornado Status 400 BAD REQUEST

```
{
    "status": 400,
    "dataHora": "2020-12-08T16:10:47.0139782-03:00",
    "erro": "Um ou mais campos estão invalidos. Faça o preenchimento correto!",
    "campos": [
        {
            "nome": "nome",
            "mensagem": "não deve ser nulo"
        }
    ]
}
```

### Id não encontrado
### Retornado Status 404 NOT FOUND

```
{
    "status": 404,
    "dataHora": "2020-12-08T16:11:44.6410208-03:00",
    "erro": "Id não encontrado!"
}
```



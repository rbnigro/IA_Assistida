# Payloads da API de Pacientes

Este documento descreve o contrato atualmente implementado pelo backend. Os dados abaixo sao sinteticos e servem apenas para testes locais.

## Pre-requisitos

- Backend em `http://localhost:8080`.
- Banco `Fortec` com a tabela `pacientes` compativel com a entidade JPA.
- Para iniciar no PowerShell:

```powershell
$env:DB_PASSWORD = Read-Host "Senha do MySQL"
mvn spring-boot:run
```

Todas as requisicoes com corpo devem usar `Content-Type: application/json`.

## Payload completo de criacao

`POST /api/pacientes`

```json
{
  "nome": "Paciente Exemplo",
  "cpf": "12345678901",
  "dataNascimento": "1990-05-20",
  "endereco": "Rua das Flores",
  "numero": 100,
  "complemento": "A",
  "cidade": "Sao Paulo",
  "estado": "SP",
  "cep": "01001000",
  "telefone": "11987654321",
  "email": "paciente.exemplo@example.test",
  "genero": "F",
  "convenio": "Convenio A",
  "especialidade": "Clinica",
  "tipagemSanguinea": "O+",
  "fatorRh": "+",
  "alergias": "Nenhuma informada",
  "usoContinuoMedicamentos": "Nenhum informado",
  "doencasPreexistentes": "Nenhuma informada",
  "observacoes": "Registro sintetico para teste"
}
```

Campos obrigatorios: `nome`, `cpf` e `dataNascimento`. O `cpf` deve conter exatamente 11 digitos e `email`, quando informado, deve ter formato valido. Os demais campos podem ser omitidos ou enviados como `null`, respeitando os limites de tamanho definidos nos DTOs.

Exemplo PowerShell:

```powershell
$body = @'
{
  "nome": "Paciente Exemplo",
  "cpf": "12345678901",
  "dataNascimento": "1990-05-20",
  "email": "paciente.exemplo@example.test"
}
'@
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/pacientes -ContentType 'application/json' -Body $body
```

Resposta esperada: `201 Created`, com o registro criado e o cabecalho `Location` apontando para `/api/pacientes/{id}`.

## Payload de atualizacao

`PUT /api/pacientes/{id}` usa o mesmo formato do payload de criacao e substitui os campos enviados. Como o DTO exige os campos basicos, envie pelo menos `nome`, `cpf` e `dataNascimento`.

```json
{
  "nome": "Paciente Exemplo Atualizado",
  "cpf": "12345678901",
  "dataNascimento": "1990-05-20",
  "telefone": "11999999999",
  "email": "paciente.atualizado@example.test",
  "observacoes": "Dados atualizados em teste"
}
```

Exemplo:

```powershell
$body = @'
{
  "nome": "Paciente Exemplo Atualizado",
  "cpf": "12345678901",
  "dataNascimento": "1990-05-20",
  "telefone": "11999999999",
  "email": "paciente.atualizado@example.test",
  "observacoes": "Dados atualizados em teste"
}
'@
Invoke-RestMethod -Method Put -Uri http://localhost:8080/api/pacientes/1 -ContentType 'application/json' -Body $body
```

Resposta esperada: `200 OK`, com o registro atualizado.

## Consultas

```text
GET /api/pacientes/health
GET /api/pacientes
GET /api/pacientes/1
GET /api/pacientes/cpf/12345678901
GET /api/pacientes/buscar-nome?nome=Paciente%20Exemplo
```

Respostas esperadas: `200 OK`. A consulta por id, CPF ou nome inexistente retorna `404 Not Found` quando aplicavel; a listagem retorna uma lista, que pode ser vazia.

## Exclusao

```text
DELETE /api/pacientes/1
DELETE /api/pacientes/cpf/12345678901
```

Resposta esperada: `204 No Content`. Um id ou CPF inexistente retorna `404 Not Found`.

## Payloads de validacao

### Campo obrigatorio ausente

`POST /api/pacientes`

```json
{
  "cpf": "12345678901",
  "dataNascimento": "1990-05-20"
}
```

Resposta esperada: `400 Bad Request`, com corpo no formato:

```json
{
  "erro": "nome: must not be blank"
}
```

### CPF com formato invalido

```json
{
  "nome": "Paciente Invalido",
  "cpf": "123",
  "dataNascimento": "1990-05-20"
}
```

Resposta esperada: `400 Bad Request`.

### CPF duplicado

Envie novamente o payload de criacao completo sem alterar o CPF `12345678901` depois que o primeiro registro for criado.

Resposta esperada: `409 Conflict`, com corpo no formato:

```json
{
  "erro": "CPF ja cadastrado: 12345678901"
}
```

As mensagens exatas de validacao dependem da primeira restricao encontrada pelo Bean Validation; o status e o campo `erro` fazem parte do contrato atual.

## Ordem sugerida para demonstracao

1. `GET /api/pacientes/health`.
2. Criar um registro com `POST`.
3. Consultar a lista e o id retornado.
4. Buscar por nome e CPF.
5. Atualizar o registro com `PUT`.
6. Repetir o `POST` para demonstrar `409`.
7. Enviar CPF invalido para demonstrar `400`.
8. Excluir com `DELETE` e confirmar `204`.

## Limites conhecidos

- A API atualmente nao possui autenticacao ou autorizacao.
- O transporte local e HTTP, sem TLS.
- A integracao efetiva com MySQL ainda precisa ser executada no ambiente local com o schema correto.
- O frontend nativo esta disponivel em `Codigo/academic-crud-frontend` e usa este contrato por meio do proxy local do Vite.
- Nao registrar CPFs, nomes, emails ou outros dados reais em commits, exemplos ou relatorios.

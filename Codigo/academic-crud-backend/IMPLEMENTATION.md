# Registro da implementacao

## Estado atual

- `author_agent_id`: `github-copilot`
- `verification_state`: compilacao e testes automatizados verificados; integracao MySQL real ainda pendente
- `scope`: esqueleto funcional do backend Spring Boot 3 para o CRUD de `Paciente`
- `frontend`: implementado em TypeScript, HTML e CSS na pasta `academic-crud-frontend`, com proxy Vite para a API
- `IA`: o CRUD nao depende de servico de IA
- `seguranca`: autenticacao, autorizacao e TLS ainda nao fazem parte desta etapa

## O que foi implementado

- Entidade JPA `Paciente` mapeada para `pacientes`, sem Lombok e com CPF unico.
- DTOs `PacienteRequestDTO`, `PacienteUpdateDTO` e `PacienteResponseDTO` como records.
- Repositorio com listagem, busca parcial por nome, busca por CPF, verificacao de duplicidade e exclusao por CPF.
- Servico com criacao, atualizacao, consultas, exclusao, transacoes e tratamento de CPF duplicado ou paciente inexistente.
- Controller em `/api/pacientes` com health check e endpoints CRUD.
- `ApiExceptionHandler` com respostas `400`, `404` e `409` no formato `{ "erro": "..." }`.
- Validacao dos campos obrigatorios, formato do CPF, email e limites de tamanho.
- Catalogo reproduzivel de requests, respostas esperadas, erros e ordem de demonstracao em `API_PAYLOADS.md`.

## Camadas e arquivos principais

- `src/main/java/com/unipds/clinica/model/Paciente.java`: entidade de dominio.
- `src/main/java/com/unipds/clinica/dto/`: contratos de entrada e saida.
- `src/main/java/com/unipds/clinica/repository/PacienteRepository.java`: acesso JPA.
- `src/main/java/com/unipds/clinica/service/PacienteService.java`: regras de negocio.
- `src/main/java/com/unipds/clinica/controller/PacienteController.java`: rotas HTTP.
- `src/main/java/com/unipds/clinica/controller/ApiExceptionHandler.java`: erros HTTP padronizados.
- `src/test/java/com/unipds/clinica/controller/PacienteControllerTest.java`: health check, 404 por id e rejeicao de CPF invalido.

## Endpoints implementados

- `GET /api/pacientes/health`
- `POST /api/pacientes`
- `GET /api/pacientes`
- `GET /api/pacientes/buscar-nome?nome={nome}`
- `GET /api/pacientes/{id}`
- `GET /api/pacientes/cpf/{cpf}`
- `PUT /api/pacientes/{id}`
- `DELETE /api/pacientes/{id}`
- `DELETE /api/pacientes/cpf/{cpf}`

Os payloads necessarios para cada fluxo estao em `API_PAYLOADS.md`. Os exemplos usam somente dados sinteticos.

## Configuracao e persistencia

- Banco esperado: MySQL local `localhost:3306`, schema `Fortec` e tabela `pacientes` compativel com a entidade.
- Usuario padrao configurado: `root`.
- A senha pode ser informada por `DB_PASSWORD`; nao adicionar credenciais ao repositorio.
- `DB_URL` e `DB_USERNAME` podem substituir os valores locais padrao.
- A URL JDBC mantem `allowPublicKeyRetrieval=true` para instalacoes locais que usam `caching_sha2_password`.
- `spring.jpa.hibernate.ddl-auto=validate` exige que o schema exista e seja compativel; a aplicacao nao cria a tabela automaticamente.

## Validacoes executadas

- `mvn clean test` com JDK `21.0.12`: baseline registrado como sucesso; nesta rodada `mvn -q test` terminou com código 0 e 4 testes executados.
- Revisao independente pelo agente `Explore`: apontamentos de seguranca e corrida de CPF corrigidos.
- Validacao estrutural de `API_PAYLOADS.md`: endpoints CRUD, `400 Bad Request`, `409 Conflict` e `Content-Type` presentes.
- Nesta atualizacao, o Harness executa `npm run test` no frontend e `mvn -q test` no backend.
- A aprovacao formal do Code Reviewer ainda nao foi registrada.

## Acesso local

O servidor esta configurado para HTTP, sem SSL/TLS:

```text
http://localhost:8080/api/pacientes/health
```

Usar `https://localhost:8080` contra esta configuracao causa falha de handshake. HTTPS exige certificado e propriedades `server.ssl.*`.

## Pendencias e riscos

- Executar a integracao real contra MySQL e confirmar o schema `Fortec.pacientes`.
- Adicionar testes de contrato para POST, PUT, validacao, conflito, listagem e exclusao.
- Solicitar e registrar Code Review independente.
- Nao publicar a API fora de ambiente controlado enquanto dados pessoais e clinicos estiverem sem autenticacao/autorizacao.

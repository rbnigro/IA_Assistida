# Implementacao inicial

- `author_agent_id`: `github-copilot`
- `verification_state`: `verified` para compilacao e testes automatizados; integracao MySQL ainda `planned`
- `source`: DDL da tabela `pacientes`, `Curso/Docs/Decisao_Tecnologica.md` e perfil `Projeto/agents/developer.agent.md`
- `scope`: estrutura inicial Spring Boot 3 com Java 21 e entidade JPA `Paciente`
- `database`: MySQL com configuracao padrao local (`localhost:3306`), banco `Fortec`, servidor informado como ativo
- `configuration`: usuario padrao `root`; a senha deve ser fornecida obrigatoriamente pela variavel de ambiente `DB_PASSWORD`
- `jdbc`: a URL local habilita `allowPublicKeyRetrieval=true`, necessario para a autenticacao MySQL local com `caching_sha2_password`; ao sobrescrever `DB_URL`, preserve esse parametro quando aplicavel
- `limitation`: a conexao real depende de o schema `Fortec` conter a tabela `pacientes` compativel com a entidade

## Camadas e endpoints

- `com.unipds.clinica.model.Paciente`: entidade JPA sem geracao de codigo.
- `com.unipds.clinica.dto`: `PacienteRequestDTO`, `PacienteUpdateDTO` e `PacienteResponseDTO` como records.
- `com.unipds.clinica.repository.PacienteRepository`: persistencia e consultas por nome/CPF.
- `com.unipds.clinica.service.PacienteService`: regras de unicidade, atualizacao e exclusao.
- `com.unipds.clinica.controller.PacienteController`: API em `/api/pacientes`.
- Endpoints: `GET /health`, `POST /`, `GET /`, `GET /buscar-nome`, `GET /{id}`, `GET /cpf/{cpf}`, `PUT /{id}`, `DELETE /{id}` e `DELETE /cpf/{cpf}`.

## Validacao

- Comando: `mvn clean test` com JDK `21.0.12`.
- Resultado: `BUILD SUCCESS`; 2 testes executados, 0 falhas e 0 erros.
- Revisao independente: realizada pelo agente `Explore`; apontamentos de seguranca e corrida de CPF corrigidos. A aprovacao formal ainda depende do Code Reviewer designado.
- Seguranca: autenticacao/autorizacao ainda nao fazem parte deste esqueleto; nao publicar a API fora de ambiente controlado antes dessa camada existir, pois os DTOs incluem dados pessoais e clinicos.

## Acesso local

O servidor Spring Boot esta configurado para HTTP, sem SSL/TLS. Use:

```text
http://localhost:8080/api/pacientes/health
```

A URL `https://localhost:8080/...` envia um handshake TLS para uma porta HTTP e causa o erro `Invalid character found in method name`. HTTPS exige configurar certificado e propriedades `server.ssl.*` antes de usar esse protocolo.

## Configuracao local

No PowerShell, defina a senha da instalacao local antes de iniciar a aplicacao:

```powershell
$env:DB_PASSWORD = Read-Host "Senha do MySQL"
mvn spring-boot:run
```

Tambem e possivel substituir `DB_URL` e `DB_USERNAME` quando a instalacao local nao usar os valores padrao. A senha nao deve ser adicionada a este repositorio.

# Fatos Semanticos do Projeto

Este arquivo guarda fatos reutilizaveis e verificaveis. Um fato deve ser curto, ter uma fonte e nao depender do historico completo da conversa.

## Formato

```yaml
fact_id: SF-000
statement: fato verificavel
source: caminho ou evidencia
status: verified_documentation|verified_implementation|candidate|superseded
source_detail: secao, teste, commit ou evidencia especifica
valid_from: 2026-08-24
valid_until: null
author_agent_id: project-owner
reviewer_agent_id: explore-01
reviewer_role: documentation-review
last_verified_by: project-owner
```

## Fatos verificados

### SF-001 - Stack obrigatoria

```yaml
fact_id: SF-001
statement: O backend usa JDK 21 (sem Lombok), o frontend usa Angular 19 e o banco inicial e MySQL.
source: Projeto/CONSTITUTION.md
source_detail: Principios 1 e Limites tecnicos
status: verified_documentation
author_agent_id: project-owner
reviewer_agent_id: explore-01
reviewer_role: documentation-review
valid_from: 2026-08-24
valid_until: null
last_verified_by: project-owner
```

### SF-002 - Independencia do Code Review

```yaml
fact_id: SF-002
statement: O agente que implementa uma demanda nao pode ser seu Code Reviewer.
source: Projeto/agents/code-reviewer.agent.md
source_detail: Regra inviolavel
status: verified_documentation
author_agent_id: project-owner
reviewer_agent_id: explore-01
reviewer_role: documentation-review
valid_from: 2026-08-24
valid_until: null
last_verified_by: project-owner
```

### SF-003 - Degradacao segura

```yaml
fact_id: SF-003
statement: O CRUD deve continuar funcionando quando servicos de IA estiverem indisponiveis.
source: Projeto/CONSTITUTION.md
source_detail: Principio 3
status: verified_documentation
author_agent_id: project-owner
reviewer_agent_id: explore-01
reviewer_role: documentation-review
valid_from: 2026-08-24
valid_until: null
last_verified_by: project-owner
```

### SF-004 - Contexto minimo

```yaml
fact_id: SF-004
statement: Cada agente recebe somente os artefatos necessarios para sua funcao e tarefa.
source: Projeto/docs/methodology.md
source_detail: Controle de contexto
status: verified_documentation
author_agent_id: project-owner
reviewer_agent_id: explore-01
reviewer_role: documentation-review
valid_from: 2026-08-24
valid_until: null
last_verified_by: project-owner
```

## Regras de uso

- fatos `verified` podem ser recuperados quando relevantes;
- fatos `candidate` exigem validacao antes de orientar uma decisao;
- todo fato deve apontar para uma fonte;
- fatos contraditos devem ser marcados como `superseded`, nunca apagados sem registro;
- nao inclua segredo, PII ou afirmacao gerada sem evidencia;
- alteracoes devem registrar `author_agent_id` e `reviewer_agent_id`.

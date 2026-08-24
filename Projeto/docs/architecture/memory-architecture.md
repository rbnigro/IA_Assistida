# Arquitetura de Memoria e Contexto

Este documento define uma implementacao pratica para memoria episodica e semantica no projeto IA First. Memoria e uma capacidade opcional do produto e nao pode ser requisito para o CRUD basico.

## Diferenca entre as memorias

| Tipo | Guarda | Escopo | Exemplo |
| --- | --- | --- | --- |
| Episodica | eventos de uma tarefa | `task_id` e usuario | plano executado, falha e aprovacao |
| Semantica | fatos consolidados | dominio e permissao | definicao de entidade ou regra aprovada |
| Estado | proxima transicao | uma execucao | etapa atual, tentativas e pendencias |

Memoria episodica responde "o que aconteceu?". Memoria semantica responde "o que sabemos e podemos reutilizar?". Estado responde "o que pode acontecer agora?".

## Modelo de registro

```yaml
memory_id: mem-001
memory_type: episodic
tenant_id: public
user_id: user-123
task_id: task-001
namespace: crud.support
content: "O chamado foi consultado com scope tickets:read."
source: tool_result
confidence: verified
created_at: 2026-08-24T10:00:00Z
expires_at: 2026-08-25T10:00:00Z
sensitivity: internal
```

Campos obrigatorios:

- `memory_id` para deduplicacao;
- `memory_type` para selecionar o armazenamento;
- `user_id` ou escopo publico;
- `task_id` para memoria episodica;
- `source` e `confidence`;
- validade e sensibilidade;
- data de criacao e, quando aplicavel, expiracao.

## Persistencia sugerida

Para o primeiro incremento, use H2 com tabelas separadas e escopo explicito:

```text
conversation_events
  memory_id, tenant_id, task_id, user_id, event_type, payload,
  created_at, expires_at

semantic_memories
  memory_id, tenant_id, namespace, subject, predicate, object_value,
  source, confidence, version, created_at, expires_at

task_state
  state_id, tenant_id, task_id, user_id, stage, status, state_json, updated_at
```

A interface da aplicacao deve esconder o armazenamento:

```text
MemoryStore
  appendEpisode(event)
  searchEpisodes(scope, taskId, query, limit)
  upsertSemantic(scope, memory)
  searchSemantic(scope, namespace, query, limit)
  deleteExpired(now)
```

Uma evolucao futura pode adicionar busca vetorial para memoria semantica, mas o contrato nao deve depender de um fornecedor especifico.

`memory_id` identifica registros de memoria episodica e semantica e deve ser estavel para deduplicacao. `state_id` identifica o registro de estado da tarefa. Esses identificadores sao distintos porque memoria e estado possuem ciclos de vida diferentes.

## Ciclo de memoria episodica

1. criar `task_id` no inicio da demanda;
2. registrar somente eventos relevantes;
3. atualizar estado apos cada etapa confirmada;
4. resumir eventos antigos quando o limite for atingido;
5. recuperar episodios apenas da tarefa e do usuario autorizados;
6. expirar ou anonimizar conforme politica;
7. preservar o resumo e as evidencias necessarias para auditoria.

Nao armazene raciocinio interno bruto. Armazene entrada relevante, acao, resultado, erro, decisao e evidencia operacional.

## Ciclo de memoria semantica

1. extrair um possivel fato de documento, resultado ou decisao;
2. validar a fonte e a permissao;
3. normalizar em sujeito, predicado e valor;
4. deduplicar por namespace e versao;
5. marcar como `candidate` ou `verified`;
6. permitir uso em contexto somente conforme confianca;
7. invalidar quando a fonte ou a regra mudar.

Memoria semantica nao deve ser escrita automaticamente por qualquer resposta do modelo. Fatos de dominio exigem fonte e, quando relevantes, aprovacao ou validacao por teste. Toda leitura e escrita deve validar `tenant_id`, `user_id`, namespace e permissao antes da operacao.

## Montagem de contexto

O orquestrador deve selecionar memoria antes de chamar o agente:

```yaml
memory_query:
  tenant_id: tenant-001
  user_id: user-123
  task_id: task-001
  namespace: crud.support
  types:
    - episodic
    - semantic
  limit: 8
  max_tokens: 1800
  min_confidence: verified
```

A resposta deve separar memoria de instrucoes:

```yaml
selected_context:
  facts:
    - source: semantic_memories/mem-010
      content: "Status valido: OPEN, IN_PROGRESS, CLOSED."
  episodes:
    - source: conversation_events/mem-001
      content: "A consulta anterior retornou vazio."
  policies:
    - source: CONSTITUTION.md
      content: "Escrita exige autorizacao."
```

Politicas sempre possuem autoridade superior a memorias e dados recuperados.

## Isolamento e privacidade

- filtre por `user_id`, tenant e namespace;
- nunca reutilize memoria de um usuario em outro;
- nao grave credenciais ou PII desnecessaria;
- aplique expiracao e exclusao;
- mascare logs;
- valide autorizacao antes da leitura;
- registre quem leu e escreveu memoria;
- impeça que o usuario instrua o modelo a recuperar memoria alheia.

## Testes

O harness deve verificar:

- retomada de uma tarefa pelo `task_id`;
- isolamento entre usuarios;
- deduplicacao de eventos;
- expiracao;
- limite de tokens;
- rejeicao de fato sem fonte;
- invalidacao de memoria obsoleta;
- memoria episodica nao vazando para outra tarefa;
  - memoria semantica nao sobrescrevendo politica;
  - leitura de memoria de outro tenant sendo recusada;
  - leitura de memoria de outro usuario sendo recusada;
- comportamento do CRUD quando o armazenamento de memoria esta indisponivel.

## Criterio de pronto

A memoria esta pronta quando possui contrato, armazenamento, escopo, expiracao, auditoria, testes de isolamento e comportamento de degradacao. Uma memoria que apenas acumula o historico da conversa nao atende a este criterio.

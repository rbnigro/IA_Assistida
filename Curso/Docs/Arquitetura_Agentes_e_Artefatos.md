 # Arquitetura de Agentes e Artefatos

## Hierarquia

```text
CONSTITUTION.md -> AGENTS.md -> agentes -> prompts -> codigo e testes
									  |
									  +-> harness e avaliacao
```

## Artefatos

| Artefato | Funcao |
| --- | --- |
| `CONSTITUTION.md` | principios, limites e proibicoes |
| `AGENTS.md` | regras operacionais do repositorio |
| `agents/*.agent.md` | papel, ferramentas e limites de cada agente |
| `prompts/*.md` | instrucoes reutilizaveis para tarefas |
| `docs/` | arquitetura, decisoes e runbooks |
| `services/` | APIs, adaptadores e MCP |
| `tests/` | verificacao do comportamento |
| `harness/` | cenarios, limites, metricas e avaliacao |
| `Avaliacao/` | rubricas, avaliadores e relatorios independentes |

## Agentes

O Arquiteto define contratos. O Desenvolvedor implementa. O Code Reviewer revisa codigo produzido por outro agente. O Security Reviewer analisa riscos. Os engenheiros de RAG e MCP cuidam de suas especialidades. O Avaliador mede o resultado.

## Independencia

Toda mudanca deve registrar `author_agent_id` e `reviewer_agent_id`. A revisao e bloqueada se forem iguais ou ausentes. O Code Reviewer produz parecer e nao altera diretamente o codigo revisado.

## Contexto

Cada chamada recebe somente os artefatos necessarios, com limite de tokens e ferramentas. Estado, fatos, instrucoes e evidencias devem permanecer identificaveis. Conteudo externo e dado, nao autoridade.

## IA First

A IA pode apoiar todas as fases, mas o CRUD deve funcionar sem modelo externo. Sugestoes de IA precisam ser verificadas, testadas e revisadas antes de serem aceitas.

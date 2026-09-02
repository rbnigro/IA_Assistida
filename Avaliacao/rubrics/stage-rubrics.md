# Rubricas por Etapa

## Objetivo

Avaliar cada uma das dez etapas com criterios objetivos e evidencias reproduziveis.

## Escopo

A rubrica cobre o CRUD em JDK 21 (sem Lombok), Angular 19 e MySQL, seguido pelas capacidades IA First. Uma etapa so pode ser aprovada quando todos os criterios obrigatorios forem atendidos.

## Criterios

Pontuacao por item: `0` nao demonstrado, `1` parcial, `2` demonstrado com evidencia. Um bloqueador de seguranca, persistencia ou revisao independente impede aprovacao mesmo com pontuacao alta.

| Etapa | Criterios obrigatorios | Evidencia minima | Bloqueadores |
| --- | --- | --- | --- |
| 00 Baseline | `00-REQ`, `00-RISK`, `00-DOD`, `00-SCENARIO` | requisito, mapa de riscos, DoD e cenario feliz | escopo indefinido |
| 01 CRUD backend | `01-API`, `01-LAYERS`, `01-VALIDATION`, `01-ERRORS` | testes de endpoint, contrato e compilacao JDK 21 (sem Lombok) | API nao compila |
| 02 CRUD Angular | `02-SCREENS`, `02-FORMS`, `02-STATES`, `02-A11Y` | teste de fluxo e evidencia de acessibilidade | frontend nao consome contrato |
| 03 MySQL testado | `03-SCHEMA`, `03-CRUD`, `03-TX`, `03-PERSISTENCE` | testes de integracao MySQL e transacao | perda ou duplicacao de dados |
| 04 RAG minimo | `04-INGEST`, `04-RETRIEVE`, `04-SOURCES`, `04-ABSTAIN` | dataset com respostas e fontes esperadas | resposta sem evidencia |
| 05 Agente reativo | `05-TOOL`, `05-STATE`, `05-LIMITS`, `05-FAILURE` | trace, limite de chamadas e teste de timeout | loop ou escrita sem autorizacao |
| 06 Plan and Execute | `06-PLAN`, `06-EXECUTE`, `06-VERIFY`, `06-COMPARE` | relatorio ReAct versus plano com mesma tarefa | plano sem limite |
| 07 MCP | `07-SCHEMA`, `07-SCOPES`, `07-ADAPTER`, `07-AUDIT` | testes positivos, negativos e auditoria | acesso direto ao legado |
| 08 Multiagente | `08-ROLES`, `08-CONTRACTS`, `08-CONTEXT`, `08-CLOSE` | trace multiagente e IDs distintos de autor/revisor | autor igual ao revisor |
| 09 IA First final | `09-INTEGRATION`, `09-SECURITY`, `09-OBSERVABILITY`, `09-DELIVERY` | demo, relatorio, metricas e Release | segredo, falha critica ou falta de DoD |

## Avaliacao comum a todas as etapas

O harness completo e obrigatorio quando a etapa possui codigo ou comportamento executavel. Na etapa 00, a validacao equivalente consiste em conferir requisito, riscos, cenario feliz e Definition of Done; ADRs entram a partir da etapa de arquitetura. Nenhuma etapa pode ser aprovada sem uma forma de evidencia adequada ao seu escopo.

- requisitos e criterios de aceite identificados;
- codigo ou artefato executavel quando aplicavel;
- testes relevantes executados;
- Code Review por identidade diferente do autor;
- Security Review quando houver risco;
- custo, horas, latencia e falhas registrados;
- documentacao e ADR atualizados;
- nenhuma evidencia inventada;
- nenhum segredo versionado.

## Exemplos

### Aprovacao

Uma etapa pode ser aprovada quando seus testes passam, o relatorio aponta evidencias, a revisao independente foi concluida e nenhum bloqueador foi encontrado.

### Bloqueio

A etapa deve ser bloqueada quando o CRUD deixa de funcionar sem IA, quando uma chamada de ferramenta nao autorizada e aceita ou quando a identidade do Code Reviewer esta ausente.

## Registro da avaliacao

```yaml
stage_id: etapa-01-crud-backend
score: 0
items:
  - criterion_id: 01-API
    score: 0
    evidence: tests/api-report.txt
blockers: []
decision: blocked
reviewer_agent_id: evaluator-01
```

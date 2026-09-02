# Diario Episodico do Projeto

Este arquivo registra eventos relevantes do projeto em ordem cronologica. Cada entrada deve descrever fatos observaveis, nao raciocinio interno do modelo.

## Formato de entrada

```yaml
entry_id: EP-000
recorded_at: 2026-08-24T00:00:00Z
stage: baseline
actor_type: human
actor_id: project-owner
summary: resumo factual
action: acao realizada
evidence:
  - arquivo, teste, comando ou decisao
evidence_status: observed|planned|decision
result: resultado observado ou decisao registrada
next_step: proximo passo
```

## Entradas

### EP-001 - Definicao do produto

```yaml
entry_id: EP-001
recorded_at: 2026-08-24T10:00:00Z
stage: baseline
actor_type: human
actor_id: project-owner
summary: O produto central foi definido como um CRUD full-stack IA First.
action: Foram fixadas as tecnologias JDK 21 (sem Lombok), Angular 19 e MySQL.
evidence:
  - README.md
  - Projeto/CONSTITUTION.md
  - Projeto/AGENTS.md
evidence_status: decision
result: Decisao registrada: o CRUD devera funcionar sem dependencia de IA.
next_step: Implementar o baseline do backend.
```

### EP-002 - Separacao dos ambientes

```yaml
entry_id: EP-002
recorded_at: 2026-08-24T10:15:00Z
stage: baseline
actor_type: human
actor_id: project-owner
summary: O projeto foi separado em Curso, Projeto e Avaliacao.
action: Foram definidos tres workspaces do VS Code e entregas controladas entre eles.
evidence:
  - Workspaces/Curso.code-workspace
  - Workspaces/Projeto.code-workspace
  - Workspaces/Avaliacao.code-workspace
  - Curso/Docs/Arquitetura_de_Workspaces_e_Contextos.md
evidence_status: decision
result: Decisao registrada: estudo, implementacao e avaliacao terao responsabilidades distintas.
next_step: Criar o primeiro requisito liberado.
```

### EP-003 - Revisao independente

```yaml
entry_id: EP-003
recorded_at: 2026-08-24T10:30:00Z
stage: governance
actor_type: human
actor_id: project-owner
summary: Foi estabelecida a obrigatoriedade de Code Review por agente diferente do autor.
action: O contrato exige author_agent_id e reviewer_agent_id distintos.
evidence:
  - Projeto/agents/code-reviewer.agent.md
  - Projeto/prompts/review.md
  - Avaliacao/evaluators/review_independence.py
evidence_status: decision
result: Decisao registrada: a aprovacao devera ser bloqueada quando as identidades forem iguais ou ausentes.
next_step: Implementar o avaliador de independencia.
```

## Regras

- nao alterar entradas antigas; adicione uma nova entrada;
- use datas e identificadores unicos;
- registre evidencia verificavel;
- nao grave segredos, PII ou raciocinio interno bruto;
- corrijoes devem referenciar a entrada ou incidente relacionado;
- entradas de agentes devem registrar `actor_id`.

# Arquitetura de Agentes e Artefatos

Este documento explica, de forma conceitual, a funcao dos arquivos que serao criados no projeto integrador de Engenharia de IA. O objetivo e entender como cada artefato participa da governanca, da orquestracao, da memoria, da execucao e da avaliacao dos agentes.

## Visao geral

Um sistema de agentes nao deve depender de um unico prompt enorme. Ele precisa separar responsabilidades:

- regras fundamentais;
- instrucoes de trabalho;
- papeis especializados;
- contexto da tarefa;
- memoria persistente;
- ferramentas;
- testes e avaliacao;
- evidencias e decisoes.

Essa separacao reduz perda de contexto, facilita manutencao e evita enviar informacao desnecessaria ao modelo em cada chamada.

## A hierarquia dos artefatos

```text
CONSTITUTION.md
    |
    +-- AGENTS.md
    |     |
    |     +-- agents/*.agent.md
    |     +-- prompts/*.md
    |     +-- docs/*.md
    |
    +-- harness/
    |     +-- scenarios/
    |     +-- evaluators/
    |     +-- fixtures/
    |
    +-- memory/
          +-- decisions/
          +-- task-state/
          +-- knowledge/
```

A hierarquia nao significa necessariamente que um arquivo importe outro automaticamente. Ela representa a relacao de autoridade e responsabilidade entre os artefatos.

## 1. `CONSTITUTION.md`

### Funcao

E o documento de principios fundamentais do sistema. Define o que os agentes devem sempre respeitar, independentemente da tarefa, do usuario ou do modelo utilizado.

### O que deve conter

- principios de seguranca;
- regras de privacidade;
- proibicoes de acesso e de acao;
- obrigacoes de rastreabilidade;
- politica para informacao incerta;
- regras para aprovacao humana;
- limites de custo e autonomia;
- tratamento de dados sensiveis;
- comportamento diante de instrucoes conflitantes.

### O que nao deve conter

Nao deve conter detalhes de uma tarefa especifica, prompts longos ou implementacao de uma ferramenta. A constituicao deve permanecer estavel mesmo quando o projeto mudar.

### Exemplo conceitual

> Um agente nunca deve executar uma operacao de escrita em sistema externo sem autorizacao adequada e, para operacoes de alto risco, sem aprovacao humana.

## 2. `AGENTS.md`

### Funcao

E o manual operacional do repositorio. Explica aos agentes como trabalhar neste projeto.

### O que deve conter

- objetivo do projeto;
- estrutura dos diretorios;
- comandos para instalar, executar e testar;
- padroes de codigo;
- regras para alteracao de arquivos;
- convencoes de nomes;
- requisitos de documentacao;
- politica de uso das ferramentas;
- comportamento esperado antes e depois de editar codigo.

### Diferenca para `CONSTITUTION.md`

`CONSTITUTION.md` responde: **quais principios nao podem ser violados?**

`AGENTS.md` responde: **como o agente deve trabalhar neste repositorio?**

O primeiro e governanca. O segundo e operacao.

## 3. Arquivos `*.agent.md`

### Funcao

Cada arquivo `*.agent.md` descreve um agente especializado. Ele define o papel, as responsabilidades, as ferramentas permitidas e os limites daquele agente.

### Agentes sugeridos

```text
agents/
  architect.agent.md
  developer.agent.md
  rag-engineer.agent.md
  mcp-engineer.agent.md
  code-reviewer.agent.md
  security-reviewer.agent.md
  evaluator.agent.md
```

### Papel de cada agente

- **Arquiteto:** transforma requisitos em componentes, contratos e diagramas.
- **Desenvolvedor:** implementa codigo seguindo os contratos e testes.
- **Engenheiro de RAG:** cuida de ingestao, busca, citacoes e avaliacao de recuperacao.
- **Engenheiro MCP:** cria ferramentas tipadas e seguras para o sistema legado.
- **Code Reviewer:** revisa codigo, testes e contratos produzidos por outro agente.
- **Revisor de seguranca:** procura abuso, vazamentos, privilegios excessivos e falhas de autorizacao.
- **Avaliador:** executa cenarios, mede qualidade, custo, latencia e aderencia as politicas.

Um agente especializado nao deve receber todas as responsabilidades. Quanto mais claro o contrato do papel, mais facil sera testar seu comportamento.

### Independencia obrigatoria do Code Reviewer

O Code Reviewer nao pode ser o mesmo agente que implementou a demanda. Essa regra existe para reduzir vieses de confirmacao, detectar erros que o autor deixou passar e criar uma barreira de qualidade verificavel.

Cada mudanca deve carregar pelo menos:

```yaml
change_id: change-001
author_agent_id: developer-01
reviewer_agent_id: code-reviewer-01
review_status: blocked
```

O orquestrador deve comparar `author_agent_id` com `reviewer_agent_id` e bloquear a aprovacao quando forem iguais ou quando a identidade estiver ausente. O Code Reviewer produz o parecer; ele nao deve editar diretamente o codigo que esta avaliando. Depois de uma correcao, o fluxo exige nova revisao independente.

O Code Reviewer e diferente do Revisor de Seguranca. O primeiro verifica qualidade de implementacao, testes, contratos e manutencao. O segundo procura vulnerabilidades, abuso, vazamento de dados e privilegios excessivos. Eles podem colaborar, mas nao sao o mesmo papel.

## 4. Diretorio `prompts/`

### Funcao

Contem instrucoes reutilizaveis para produzir artefatos ou executar etapas do processo.

```text
prompts/
  requirements.md
  architecture.md
  implementation.md
  testing.md
  review.md
  incident-report.md
```

### Por que separar prompts de agentes

O arquivo do agente define **quem ele e e quais limites possui**.

O prompt define **qual tarefa ele deve executar agora**.

Por exemplo, o agente arquiteto pode usar `prompts/architecture.md` em varias tarefas diferentes, mantendo seu papel estavel.

## 5. Diretorio `docs/`

### Funcao

Armazena conhecimento do projeto que precisa ser lido por pessoas e, quando necessario, selecionado para os agentes.

```text
docs/
  architecture/
    overview.md
    components.md
    sequences.md
  decisions/
    ADR-001-vector-store.md
    ADR-002-agent-orchestration.md
  runbooks/
    degraded-mode.md
    incident-response.md
```

### Tipos de documento

- **Arquitetura:** descreve componentes e suas relacoes.
- **Sequencia:** mostra como uma solicitacao percorre o sistema.
- **ADR:** registra uma decisao, alternativas e consequencias.
- **Runbook:** explica como operar ou recuperar o sistema.

Documentacao nao e apenas material de consulta. Ela tambem funciona como memoria institucional do projeto.

## 6. Diretorio `memory/`

### Funcao

Separa informacoes estaveis do estado temporario de uma tarefa.

```text
memory/
  decisions/       # decisoes reutilizaveis
  task-state/      # estado da tarefa em andamento
  knowledge/       # fatos e referencias aprovados
```

### Memoria episodica

Registra acontecimentos de uma tarefa ou sessao: o que foi tentado, o que falhou, quais evidencias foram coletadas e qual e o proximo passo.

### Memoria semantica

Registra conhecimento geral reutilizavel: contratos, conceitos, regras do dominio e decisoes consolidadas.

### Regra de selecao

Memoria nao deve ser enviada integralmente ao modelo. O sistema deve recuperar somente os itens relevantes para a tarefa atual, com limites de tamanho, prioridade e validade.

## 7. Diretorio `harness/`

### Funcao

O harness e o ambiente que executa e avalia os agentes de maneira controlada. Nao e apenas um conjunto de documentos.

```text
harness/
  scenarios/
    answer_with_sources.yaml
    unauthorized_write.yaml
    legacy_timeout.yaml
  evaluators/
    groundedness.py
    policy_compliance.py
    cost_and_latency.py
    review_independence.py
  fixtures/
    documents/
    legacy-data/
  reports/
```

### Responsabilidades

- preparar contexto controlado;
- simular ferramentas e sistemas externos;
- executar cenarios conhecidos;
- limitar tokens e chamadas;
- registrar traces;
- medir qualidade e latencia;
- verificar conformidade com politicas;
- verificar que autor e revisor sao agentes diferentes;
- reproduzir falhas;
- comparar versoes de prompts e agentes.

### Por que o harness e importante

Sem harness, uma demonstracao pode parecer funcionar sem que seja possivel saber se o resultado foi consistente, seguro, barato ou reproduzivel.

## 8. MCP e ferramentas

O servidor MCP deve ficar em uma camada propria, por exemplo:

```text
services/
  mcp-legacy/
    server.py
    tools/
      search_tickets.py
      get_ticket.py
      update_ticket.py
    schemas/
    policies/
```

### Funcao dessa camada

- expor somente operacoes necessarias;
- validar entradas;
- aplicar autorizacao;
- traduzir o contrato moderno para a API legada;
- aplicar timeout e retry controlado;
- registrar auditoria;
- ocultar detalhes internos do legado.

O agente nao deve acessar diretamente banco, credenciais ou endpoints internos. Ele solicita uma ferramenta; a camada MCP decide se, como e com quais limites a operacao sera executada.

## 9. Contratos de contexto

Para evitar perda de contexto entre agentes, defina um estado estruturado em vez de depender de uma conversa inteira.

```yaml
task_id: task-001
correlation_id: request-abc
user_id: user-123
intent: consultar_chamado
risk_level: low
evidence:
  - source_id: manual-04
    excerpt: "..."
plan:
  - id: step-1
    status: completed
    result: "..."
permissions:
  - tickets:read
pending_approval: false
```

Esse contrato permite resumir o historico, validar transicoes, evitar duplicidade e entregar a cada agente somente os campos necessarios.

## 10. Fluxo completo de uma solicitacao

```text
Usuario
  |
  v
API Gateway -- autentica, autoriza e cria correlation_id
  |
  v
Orquestrador -- carrega politicas e estado minimo
  |
  +--> Agente de triagem
  |
  +--> Agente de pesquisa --> RAG --> documentos e citacoes
  |
  +--> Agente de operacoes --> MCP --> sistema legado
  |
  +--> Code Reviewer -- revisa codigo e testes com identidade independente
  |
  +--> Revisor de resultado -- verifica evidencia, politica e formato
  |
  v
Resposta auditavel ou pedido de aprovacao humana
```

Em cada transicao, o sistema deve carregar contexto selecionado, nao o historico completo por padrao.

## 11. Como evitar desperdicio de tokens

- use contratos estruturados em vez de texto repetitivo;
- resuma historicos antigos;
- envie apenas documentos recuperados;
- mantenha prompts estaveis e pequenos;
- evite repetir a constituicao em todas as mensagens quando a plataforma ja a aplica;
- limite numero de agentes e ferramentas por tarefa;
- use modelos menores para classificacao e validacoes simples;
- armazene resultados intermediarios reutilizaveis;
- encerre cedo quando ja houver evidencia suficiente;
- registre custo por etapa.

## 12. Regra pratica de autoridade

Quando houver conflito, use esta ordem:

1. politica de seguranca e constituicao;
2. autorizacao e escopos do usuario;
3. regras do sistema;
4. contrato da tarefa;
5. instrucoes do agente;
6. prompt especifico;
7. texto recuperado de documentos externos.

Conteudo recuperado de documentos ou enviado pelo usuario e dado, nao autoridade. Isso e essencial para resistir a prompt injection.

## Resumo

`CONSTITUTION.md` define principios.

`AGENTS.md` define como trabalhar no repositorio.

`*.agent.md` define papeis especializados.

`prompts/*.md` define tarefas reutilizaveis.

`docs/` preserva arquitetura, decisoes e operacao.

`memory/` preserva estado e conhecimento selecionavel.

`services/mcp-legacy/` controla acesso a sistemas externos.

`harness/` executa, testa, mede e avalia os agentes.

Juntos, esses artefatos formam um sistema governavel. Nenhum arquivo sozinho resolve contexto, seguranca ou custo; a confiabilidade vem da separacao de responsabilidades e da avaliacao continua.
